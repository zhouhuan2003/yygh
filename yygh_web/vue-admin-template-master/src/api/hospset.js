import request from '@/utils/request'

export default{
  getHospSetList(PageNum,PageSize,searchObj){
    return request({
      url: `/admin/hosp/hospitalSet/selectByPage/${PageNum}/${PageSize}`,
      method: 'post',
      data: searchObj
    })
  },
  removeDataById(id){
    return request({
      url:  `/admin/hosp/hospitalSet/delete/${id}`,
      method: 'delete'
    })
  },
  //批量删除
  batchHospSet(ids){
    return request({
      url:  `/admin/hosp/hospitalSet/deleteByIds`,
      method: 'delete',
      data: ids
    })
  },
  //锁定和取消锁定
  lockHospSet(id,status){
    return request({
      url:  `/admin/hosp/hospitalSet/lock/${id}/${status}`,
      method: 'put'
    })
  },
  //添加
  saveHospSet(hospitalSet){
    return request({
      url:  `/admin/hosp/hospitalSet/save`,
      method: 'post',
      data: hospitalSet
    })
  },
  //根据id查询
  getById(id){
    return request({
      url: `/admin/hosp/hospitalSet/getById/${id}`,
      method: 'get'
    })
  },
  //修改
  updateHospSet(hospitalSet){
    return request({
      url:  `/admin/hosp/hospitalSet/update`,
      method: 'post',
      data: hospitalSet
    })
  }
}
