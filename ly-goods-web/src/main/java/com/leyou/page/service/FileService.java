package com.leyou.page.service;

/**
 * @Author: Gray
 */
public interface FileService {

    void createHtml(Long id) throws Exception;

    boolean exists(Long id);

    void syncCreateHtml(Long id);

    void deleteHtml(Long id);
}
