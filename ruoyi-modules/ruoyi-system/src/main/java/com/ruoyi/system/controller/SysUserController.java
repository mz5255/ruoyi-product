package com.ruoyi.system.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.domain.SysCity;
import com.ruoyi.system.domain.vo.*;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.*;
import com.ruoyi.system.utils.UserUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.annotations.Param;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.InnerAuth;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.domain.SysDept;
import com.ruoyi.system.api.domain.SysRole;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.model.LoginUser;
import com.ruoyi.system.repository.SysUserRepository;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/user")
public class SysUserController extends BaseController
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private ISysPermissionService permissionService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysCityService iSysCityService;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    UserUtil userUtil;
    @Autowired
    SysUserMapper userMapper;

    /**
     * 增量同步到es
     */
    @PostMapping("updateEs")
    public void updateEs(@RequestBody SysUser sysUser){
            sysUserRepository.save(sysUser);
    }



    /**
     * 工作流获取申请单号以及 申请人
     */
    @GetMapping("getNumAndUsername")
    public Map getNumAndUsername(){
        String num = new Snowflake().nextIdStr();
        String username = SecurityUtils.getUsername();
        HashMap<String, Object> map = new HashMap<>();
        map.put("num",num);
        map.put("username",username);
        return map;
    }


    /**
     * 根据用户名获取用户
     */
    @PostMapping("getUserByUsername")
    public SysUser getUserByUsername(@RequestParam("username") String username){
        SysUser sysUser = userService.selectUserByUserNameByDelFlag(username);
        return sysUser;
    }



    /**
     *获取最早创建用户 以及 获取最晚创建用户
     */
    @GetMapping("getListByCreated")
    public Map getUserListByCreated(){
        HashMap<String, Object> map = new HashMap<>();
        //获取最早创建用户
      List<SysUser> minSysUser =  userService.selectUserByMinCreated();
       //获取最晚创建用户
        List<SysUser> maxSysUser =  userService.selectUserByMaxCreated();
        //获取各个时间段的注册人数
      EchartsVo echartsVo = userService.selectUserListByCreated();

        ArrayList<Object> list = new ArrayList<>();


        //饼图
        EcharsVoOne echarsVoOne = new EcharsVoOne();
        echarsVoOne.setName("2020-2022");
        echarsVoOne.setValue(echartsVo.getVOne());
        list.add(echarsVoOne);

        EcharsVoTwo echarsVoOne1 = new EcharsVoTwo();
        echarsVoOne1.setName("2022-2024");
        echarsVoOne1.setValue(echartsVo.getVTwo());
        list.add(echarsVoOne1);

        EcharsVoThree echarsVoOne2 = new EcharsVoThree();
        echarsVoOne2.setName("2024-2026");
        echarsVoOne2.setValue(echartsVo.getVThree());
        list.add(echarsVoOne2);

        EcharsVoFour echarsVoOne3 = new EcharsVoFour();
        echarsVoOne3.setName("2026-2028");
        echarsVoOne3.setValue(echartsVo.getVFour());
        list.add(echarsVoOne3);

        //折线图
        ArrayList<String> strings = new ArrayList<>();
        strings.add("2020-2022");
        strings.add("2022-2024");
        strings.add("2024-2026");
        strings.add("2026-2028");
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(echartsVo.getVOne());
        integers.add(echartsVo.getVTwo());
        integers.add(echartsVo.getVThree());
        integers.add(echartsVo.getVFour());

        map.put("name",strings);
        map.put("value",integers);
        map.put("data",list);
        map.put("minSysUser",minSysUser);
        map.put("maxSysUser",maxSysUser);
        return map;
    }





    /**
     * 获取用户列表
     * @return
     */
    @GetMapping("/userList")
    public HashMap<String,Object> getUsetList(){
        HashMap<String, Object> map = new HashMap<>();
        List list = userService.MySelectUserList();
        map.put("list",list);
        return map;
    }

    /**
     * 获取城市列表
     */
    @GetMapping("/cityList")
    public  Map<String,Object> getCityList(){

        List<SysCity> list =null;
        if(redisTemplate.hasKey("cityList")){
            list= redisTemplate.opsForList().range("cityList",0,-1);
        }else{
            list = iSysCityService.myList(0);
            redisTemplate.opsForList().rightPushAll("cityList",list);
            redisTemplate.expire("cityList",30, TimeUnit.MINUTES);
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("list", list);
        return map;
    }

    /**
     * elasticsearch 查询方法
     */

    @GetMapping("esList")
    public Map<String,Object> getEsList(SysUser sysUser, QueryPage queryPage) throws ParseException {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //逻辑删除  只有等于0的才能查出来
        boolQueryBuilder.must(QueryBuilders.termQuery("delFlag",sysUser.getDelFlag()));
        //用户名模糊查询
        if(StringUtils.isNotEmpty(sysUser.getUserName())){
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("userName","*"+sysUser.getUserName().toLowerCase(Locale.ROOT)+"*"));
        }
        //手机号精确查询
        if(sysUser.getPhonenumber()!=null){
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("phonenumber","*"+sysUser.getPhonenumber().toLowerCase(Locale.ROOT)+"*"));
        }
        //状态精确查询
        if(StringUtils.isNotEmpty(sysUser.getStatus())){
            boolQueryBuilder.must(QueryBuilders.termQuery("status",sysUser.getStatus()));
        }
        //区间查询
        Date beginTime = DateUtils.parseDate((String) sysUser.getParams().get("beginTime"));
        Date endTime = DateUtils.parseDate((String) sysUser.getParams().get("endTime"));
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("createTime");
        if(beginTime!=null){
            rangeQuery.gte(beginTime.getTime());
            boolQueryBuilder.must(rangeQuery);
        }
        if(endTime!=null){
            rangeQuery.lte(endTime.getTime());
            boolQueryBuilder.must(rangeQuery);
        }


        //分页
        PageRequest pageRequest = PageRequest.of(queryPage.getPageNum() - 1, queryPage.getPageSize(), Sort.Direction.ASC, "userId");

        // 高亮
        HighlightBuilder.Field[] fields = new HighlightBuilder.Field[1];
        fields[0] = new HighlightBuilder.Field("userName")
                .preTags("<font color='red'>")
                .postTags("</font>");
        //创建查询对象
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(pageRequest)
                .withHighlightFields(fields)
                .build();


        //获取被选中的集合
        SearchHits<SysUser> search = elasticsearchRestTemplate.search(query, SysUser.class);
        List<SearchHit<SysUser>> searchHits = search.getSearchHits();//获取被选中集合
        ArrayList<SysUser> list = new ArrayList<>();
        searchHits.forEach(r->{
            SysUser content = r.getContent();
            //处理高亮
            if(fields!=null){
                Map<String, List<String>> highlightFields = r.getHighlightFields();
                if(highlightFields!=null){
                    List<String> userName = highlightFields.get("userName");
                    if(userName!=null){
                        String hightName = userName.get(0);
                        content.setUserName(hightName);//覆盖之前内容
                    }
                }
            }
            list.add(content);
        });
        HashMap<String, Object> map = new HashMap<>();
        map.put("list",list);
        map.put("total",search.getTotalHits());//获取总条数
        return map;
    }

    /**
     * elasticsearch初始化列表
     */
    @PostConstruct
    public void init(){
        List<SysUser> sysUsers = userService.selectUserList(new SysUser());
        sysUserRepository.saveAll(sysUsers);
    }
    /**
     * 获取用户列表
     */
    @RequiresPermissions("system:user:list")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user)
    {
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @RequiresPermissions("system:user:export")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUser user)
    {
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.exportExcel(response, list, "用户数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("system:user:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.importTemplateExcel(response, "用户数据");
    }

    /**
     * 获取当前用户信息
     */
    @InnerAuth
    @GetMapping("/info/{username}")
    public R<LoginUser> info(@PathVariable("username") String username)
    {
        SysUser sysUser = userService.selectUserByUserName(username);
        if (StringUtils.isNull(sysUser))
        {
            return R.fail("用户名或密码错误");
        }
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(sysUser);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(sysUser);
        LoginUser sysUserVo = new LoginUser();
        sysUserVo.setSysUser(sysUser);
        sysUserVo.setRoles(roles);
        sysUserVo.setPermissions(permissions);
        return R.ok(sysUserVo);
    }




    /**
     * 注册用户信息
     */
    @InnerAuth
    @PostMapping("/register")
    public R<Boolean> register(@RequestBody SysUser sysUser)
    {
        String username = sysUser.getUserName();
        if (!("true".equals(configService.selectConfigByKey("sys.account.registerUser"))))
        {
            return R.fail("当前系统没有开启注册功能！");
        }
        if (!userService.checkUserNameUnique(sysUser))
        {
            return R.fail("保存用户'" + username + "'失败，注册账号已存在");
        }
        return R.ok(userService.registerUser(sysUser));
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = userService.selectUserById(SecurityUtils.getUserId());
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 根据用户编号获取详细信息
     */
    @RequiresPermissions("system:user:query")
    @GetMapping(value = { "/", "/{userId}" })
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId)
    {
        userService.checkUserDataScope(userId);
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        ajax.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(userId))
        {
            SysUser sysUser = userService.selectUserById(userId);
            ajax.put(AjaxResult.DATA_TAG, sysUser);
            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));
        }
        return ajax;
    }

    /**
     * 新增用户
     */
    @RequiresPermissions("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user)
    {
        if (!userService.checkUserNameUnique(user))
        {
            return error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user))
        {
            return error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user))
        {
            return error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(SecurityUtils.getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));

        //全量同步到es
        userUtil.fullSync(user.getUserId());
        return toAjax(userService.insertUser(user));
    }





    /**
     * 自定义修改用户
     */
    @PostMapping("updateUser")
    public void updateUser(@RequestBody SysUser sysUser){
        userMapper.updateUser(sysUser);
    }

    /**
     * 修改用户
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        if (!userService.checkUserNameUnique(user))
        {
            return error("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber()) && !userService.checkPhoneUnique(user))
        {
            return error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail()) && !userService.checkEmailUnique(user))
        {
            return error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(SecurityUtils.getUsername());


        //全量同步到es
        userUtil.fullSync(user.getUserId());

        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @RequiresPermissions("system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        if (ArrayUtils.contains(userIds, SecurityUtils.getUserId()))
        {
            return error("当前用户不能删除");
        }
        for (Long userId : userIds) {
            //全量同步到es
            SysUser sysUser = userService.selectUserById(userId);
            sysUser.setDelFlag("2");
            sysUserRepository.save(sysUser);
        }


        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(SecurityUtils.getUsername());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user)
    {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        user.setUpdateBy(SecurityUtils.getUsername());

//        全量同步到es
        userUtil.fullSync(user.getUserId());

        return toAjax(userService.updateUserStatus(user));
    }

    /**
     * 根据用户编号获取授权角色
     */
    @RequiresPermissions("system:user:query")
    @GetMapping("/authRole/{userId}")
    public AjaxResult authRole(@PathVariable("userId") Long userId)
    {
        AjaxResult ajax = AjaxResult.success();
        SysUser user = userService.selectUserById(userId);
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        ajax.put("user", user);
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return ajax;
    }

    /**
     * 用户授权角色
     */
    @RequiresPermissions("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    public AjaxResult insertAuthRole(Long userId, Long[] roleIds)
    {
        userService.checkUserDataScope(userId);
        userService.insertUserAuth(userId, roleIds);
        return success();
    }

    /**
     * 获取部门树列表
     */
    @RequiresPermissions("system:user:list")
    @GetMapping("/deptTree")
    public AjaxResult deptTree(SysDept dept)
    {
        return success(deptService.selectDeptTreeList(dept));
    }
}
