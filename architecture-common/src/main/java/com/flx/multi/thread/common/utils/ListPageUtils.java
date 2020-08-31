package com.flx.multi.thread.common.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Fenglixiong
 * @Date: 2020/6/22 18:32
 * @Description:
 */
public class ListPageUtils {

    /**
     * 开始分页
     * @param list
     * @param curPage 页码
     * @param pageSize 每页多少条数据
     * @return
     */
    public static <T> List<T> startPage(List<T> list, int curPage, int pageSize) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        int fromIndex; // 开始索引
        int endIndex; // 结束索引
        int totalSize = list.size(); // 记录总数
        int totalPage = PageUtils.getTotalPage(totalSize,pageSize); // 页数
        if (curPage != totalPage) {
            fromIndex = (curPage - 1) * pageSize;
            endIndex = fromIndex + pageSize;
        } else {
            fromIndex = (curPage - 1) * pageSize;
            endIndex = totalSize;
        }

        return list.subList(fromIndex, endIndex);

    }

    /**
     * 返回list分页后的数据
     * @param dataList
     * @param page
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> List<T> pageList(List<T> dataList,int page,int pageSize){
        return dataList.stream().skip((page-1) * pageSize).limit(pageSize).collect(Collectors.toList());
    }

    /**
     * 分页显示list
     * @param dataList
     * @param pageSize
     * @param <T>
     */
    public static <T> void showPageList(List<T> dataList,int pageSize){
        int page = (dataList.size()+pageSize-1)/pageSize;
        System.out.println("page = "+page);
        for (int i=1;i<=page;i++){
            System.out.println(pageList(dataList,i,pageSize));
        }
    }

    public static void main(String[] args) {
        List<Integer> ageList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        showPageList(ageList,5);
    }

}
