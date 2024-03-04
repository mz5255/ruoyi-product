<template>
  <div>
    <el-row type="flex" class="row-bg" justify="center">

      <div  id="mainCreated" style="width: 20%;height: 650px;margin-left: 100px;
           margin-top: 50px;">
      </div>
      <div  id="main" style="width: 20%;height: 650px;margin-left: 100px;
           margin-top: 50px;">
      </div>
      <div  id="mainZhu" style="width: 20%;height: 650px;margin-left: 100px;
           margin-top: 50px;">
      </div>
    </el-row>
  </div>
</template>

<script>
import {getCountList} from "@/api/system/echars";
import * as echarts from 'echarts';
import Enumerable from 'linq';
export default {
  data() {
    return {
      max : 0,
    }
  },
  methods: {

    getCount(){
      getCountList().then(r=>{
        this.getBing(r.data);
        this.getZheXian(r.name,r.value);
        this.getZhu(r.name,r.value);

      })
    },
    getZhu(name,value){


      value.forEach(r=>{
        if(r.value == 12){
          r.value = {
            value: 12,
            itemStyle: {
              color: '#a90000'
            }
          }
        }
      })

      var chartDom = document.getElementById('mainZhu');
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        xAxis: {
          type: 'category',
          data: name
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: value,
            type: 'bar'
          }
        ]
      };

      option && myChart.setOption(option);
    },
    getZheXian(name,value){
      var chartDom = document.getElementById('main');
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        xAxis: {
          type: 'category',
          data: name
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            data: value,
            type: 'line'
          }
        ]
      };

      option && myChart.setOption(option);

    },
    getBing(data){
      var chartDom = document.getElementById('mainCreated');
      var myChart = echarts.init(chartDom);
      var option;

      option = {
        title: {
          text: '统计每个时期注册人数',
          subtext: 'Fake Data',
          top:"550px",
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: data,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      };

      option && myChart.setOption(option);

    }

  },
  created() {
    this.getCount();
  }
}
</script>

<style scoped>

</style>
