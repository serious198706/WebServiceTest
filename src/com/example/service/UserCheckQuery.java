package com.example.service;

/**
 * Created by 岩 on 13-10-10.
 */
/// <summary>
/// 用户登录参数实体类
/// </summary>
public class UserCheckQuery
{
    /// <summary>
    /// 用户名
    /// </summary>
    public String UserName;

    /// <summary>
    ///   密匙
    /// </summary>
    public String HashKey;

    /// <summary>
    /// MAC地址
    /// </summary>
    public String MAC;

    /// <summary>
    /// 客户端系统版本
    /// </summary>
    public String OSVersion;

    /// <summary>
    ///  公钥(3位数字)
    /// </summary>
    public String RandKey;
}