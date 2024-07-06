package com.imooc.reader.service;

import com.imooc.reader.entity.Evaluation;


import java.util.List;

public interface EvaluationService {
    /**
     * 根据图书编号，查询对应书评
     * @param id 图书编号
     * @return
     */
    public List<Evaluation> selectByBookId(Long id);
}
