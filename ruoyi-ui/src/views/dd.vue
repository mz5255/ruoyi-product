<template>
  <!-- 高德地图组件 -->

  <div>
    <el-form :inline="true" :model="formInline" class="demo-form-inline">
      <el-form-item label="地区">
        <el-input v-model="formInline.value" placeholder="地区"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="ll(vv)">查询</el-button>
      </el-form-item>
    </el-form>
    <div id="panel"></div>
    <div id="container" tabindex="0"></div>


  </div>

</template>
<script>


//很关键，没有密钥不能执行下面的 placeSearch.search方法
// 必须
import AMapLoader from "@amap/amap-jsapi-loader";



window._AMapSecurityConfig = {
  securityJsCode:"99117bd3b83705cd6a91e5f41fe53238",
}
export default {
  data(){
    return{
      // 地图map对象
      map: undefined,
      formInline:{
        value : "锦州烧烤",
      },
    }
  },
  mounted(){
    //数据渲染完成后执行
    this.initMap()
  },
  methods: {
    ll(vv){
    this.formInline.value = vv;
    this.initMap()
    },
    initMap() {

      AMapLoader.load({
        key: '9dda1981c3d42442ccf3c8ed93cc817f',//必须
        version: '2.0',
        plugins: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PolyEditor', 'AMap.CircleEditor']
      }).then(AMap => {
        //1. 设置地图容器
        this.map = new AMap.Map("container", {  //设置地图容器id
          viewMode: "3D",    //是否为3D地图模式
          zoom: 5,           //初始化地图级别
          center: [105.602725, 37.076636], //初始化地图中心点位置
          resizeEnable: true
        });
        this.map.addControl(new AMap.Scale());  //米尺
        this.map.addControl(new AMap.ToolBar());  //加减
        this.map.addControl(new AMap.MapType());  //卫星图
        AMap.plugin(["AMap.PlaceSearch"], () => {
          //2.构造地点查询类
          var placeSearch = new AMap.PlaceSearch({
            pageSize: 5, // 单页显示结果条数
            pageIndex: 1, // 页码
            city: "锦州", // 兴趣点城市
            citylimit: true,  //是否强制限制在设置的城市内搜索
            map: this.map, // 展现结果的地图实例
            panel: "panel", // 结果列表将在此容器中进行展示。
            autoFitView: true // 是否自动调整地图视野使绘制的 Marker点都处于视口的可见范围
          });
          //3.关键字查询
          let address = "烧烤" //**输入你想要查询的地址**
          placeSearch.search(address, (res, status) => {
            if (res === "complete" && status.info === "OK") {
              // console.log(res)
              console.log("地理坐标及信息", status)
            }
          });
        })
      })
    },

  }
}
</script>
<style>
#container {
  width: 80%;
  height: 500px;
  margin: 10px auto;
  border: 1px solid red}
</style>

