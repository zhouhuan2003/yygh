package com.yygh.cmn.service.impl;/*
 *@author 周欢
 *@version 1.0
 */

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yygh.cmn.listener.DictListener;
import com.yygh.cmn.mapper.DictMapper;
import com.yygh.cmn.service.DictService;
import com.yygh.model.cmn.Dict;
import com.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper,Dict> implements DictService {

    @Resource
    private DictMapper dictMapper;

    @Override
    @Cacheable(value = "dict",keyGenerator = "keyGenerator")
    public List<Dict> findChildData(Integer id) {
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("parent_id",id);
        List<Dict> list = baseMapper.selectList(dictQueryWrapper);

        for (Dict dict : list) {
            Long id1 = dict.getId();
            boolean children = this.isChildren(id1);
            dict.setHasChildren(children);
        }

        return list;
    }

    //下载
    @Override
    public void exportDictData(HttpServletResponse response) {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName="dict";
        response.setHeader("Content-disposition","attachment.filename="+fileName+".xlsx");
        List<Dict> dictList = baseMapper.selectList(null);
        ArrayList<DictEeVo> list = new ArrayList<>();
        for (Dict dict:dictList){
            DictEeVo dictEeVo = new DictEeVo();
            BeanUtils.copyProperties(dict,dictEeVo);
            list.add(dictEeVo);
        }
        try {
            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict")
                    .doWrite(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @CacheEvict(value = "dict",allEntries=true)
    public void importDictData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),DictEeVo.class,new DictListener(dictMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDictName(String dictCode, String value) {
        if (StringUtils.isEmpty(dictCode)){
            QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
            dictQueryWrapper.eq("value",value);
            Dict dict = baseMapper.selectOne(dictQueryWrapper);
            return dict.getName();
        }else {
            QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
            dictQueryWrapper.eq("dict_code",dictCode);
            Dict CodeDict = baseMapper.selectOne(dictQueryWrapper);
            Long id = CodeDict.getId();
            Dict dict = baseMapper.selectOne(new QueryWrapper<Dict>().eq("parent_id", id).eq("value", value));
            return dict.getName();
        }
    }

    @Override
    public List<Dict> findByDictCode(String dictCode) {
        //根据dictCode获取对应的id
        Dict dictByDictCode = this.getDictByDictCode(dictCode);
        //根据id获取下级
        return this.findChildData(Math.toIntExact(dictByDictCode.getId()));
    }


    public Dict getDictByDictCode(String dictCode){
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("dict_code",dictCode);
        return baseMapper.selectOne(dictQueryWrapper);
    }


    //判断id下面是否有子节点
    private boolean isChildren(Long id){
        QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.eq("parent_id",id);
        Integer integer = baseMapper.selectCount(dictQueryWrapper);
        return integer>0;
    }
}
