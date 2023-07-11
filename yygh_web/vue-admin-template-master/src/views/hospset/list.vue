<template>
    <div class="app-container">
        <el-form :inline="true" class="demo-form-inline">
            <el-form-item>
                <el-input v-model="searchObj.hosname" placeholder="医院名称" />
            </el-form-item>
            <el-form-item>
                <el-input v-model="searchObj.hoscode" placeholder="医院编号" />
            </el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
        </el-form>
        <!-- banner列表 -->
        <div>
            <el-button type="danger" size="mini" @click="removeRows()">批量删除</el-button>
        </div>

        <el-table :data="list" stripe style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" />
            <el-table-column type="index" width="50" label="序号" />
            <el-table-column prop="hosname" label="医院名称" />
            <el-table-column prop="hoscode" label="医院编号" />
            <el-table-column prop="apiUrl" label="api基础路径" width="200" />
            <el-table-column prop="contactsName" label="联系人姓名" />
            <el-table-column prop="contactsPhone" label="联系人手机" />
            <el-table-column label="状态" width="80">
                <template slot-scope="scope">
                    {{ scope.row.status === 1 ? '可用' : '不可用' }}
                </template>
            </el-table-column>

            <el-table-column label="操作" width="280" align="center">
                <template slot-scope="scope">
                    <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除
                    </el-button>
                    <el-button v-if="scope.row.status==1" type="warning" size="mini" icon="el-icon-error"
                        @click="lockHospSet(scope.row.id,0)">锁定</el-button>
                    <el-button v-if="scope.row.status==0" type="primary" size="mini" icon="el-icon-success"
                        @click="lockHospSet(scope.row.id,1)">解锁</el-button>
                    <router-link :to="'edit/'+scope.row.id">
                        <el-button type="primary" size="mini" icon="el-icon-edit"></el-button>
                    </router-link>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination :current-page="pageNum" :page-size="pageSize" :total="total"
            style="padding:30px 0;text-align:center;" layout="total,prev,pager,next,jumper" @current-change="getList" />
    </div>
</template>

<script>
//引入接口定义的js
import hospset from "@/api/hospset";
export default {
    data() {
        return {
            ids: [],
            pageNum: 1,
            pageSize: 3,
            searchObj: {},
            list: [],
            total: 0//总记录数
        }
    },
    created() {
        this.getList()
    },
    methods: {
        //锁定
        lockHospSet(id, status) {
            hospset.lockHospSet(id, status)
                .then(rep => {
                    this.getList()
                })
        },
        //获取选择复选框的id值
        handleSelectionChange(selection) {
            this.ids = selection
        },
        getList(page = 1) {
            this.pageNum = page
            hospset.getHospSetList(this.pageNum, this.pageSize, this.searchObj)
                .then(rsp => {
                    this.list = rsp.data.records
                    this.total = rsp.data.total
                }).catch(e => {

                })
        },
        removeDataById(id) {
            this.$confirm('此操作将永久删除该医院设置, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                hospset.removeDataById(id)
                    .then(rsp => {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.getList(1)
                    })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        },
        removeRows() {
            this.$confirm('此操作将永久删除选中的医院设置, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                let ids = []
                for (var i = 0; i < this.ids.length; i++) {
                    var obj = this.ids[i]
                    var id = obj.id
                    ids.push(id)
                }
                console.log("=====" + ids);
                hospset.batchHospSet(ids)
                    .then(rsp => {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.getList(1)
                    })
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });
            });
        }
    }
}
</script>