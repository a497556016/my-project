<template>
    <div style="padding: 25px">
        <a-row style="margin-bottom: 25px;">
            <a-col :span="24">
                <a-radio-group defaultValue="1" buttonStyle="solid">
                    <a-radio-button value="1">今日</a-radio-button>
                    <a-radio-button value="2">昨日</a-radio-button>
                    <a-radio-button value="3">3日</a-radio-button>
                    <a-radio-button value="4">7日</a-radio-button>
                </a-radio-group>
                <a-date-picker style="margin-left: 10px;"></a-date-picker>
            </a-col>
        </a-row>
        <a-row type="flex" justify="space-between" align="middle">
            <a-col :span="4">
                <div class="c-box">
                    <div class="c-count">130</div>
                    <div class="c-title" :style="{backgroundColor: '#7873e0'}">总计</div>
                </div>
            </a-col>
            <a-col :span="4">
                <div class="c-box">
                    <div class="c-count">23</div>
                    <div class="c-title" :style="{backgroundColor: '#20c443'}">人数</div>
                </div>
            </a-col>
            <a-col :span="4">
                <div class="c-box">
                    <div class="c-count">0</div>
                    <div class="c-title" :style="{backgroundColor: '#cb0602'}">失败数</div>
                </div>
            </a-col>
            <a-col :span="4">
                <div class="c-box">
                    <div class="c-count">30%</div>
                    <div class="c-title" :style="{backgroundColor: '#3dcbbc'}">完成率</div>
                </div>
            </a-col>
        </a-row>
        <a-divider />
        <a-row style="margin-top: 65px">
            <a-col :span="12">
                <div id="chart1" style="height: 300px;"></div>
            </a-col>
            <a-col :span="12">
                <div id="chart2" style="height: 300px;"></div>
            </a-col>
        </a-row>
        <a-divider />
        <a-row>
            <a-col :span="24">
                <m-table :columns="columns" :data="taskData">
                    <template slot="action">
                        <a-button>处理</a-button>
                    </template>
                </m-table>
            </a-col>
        </a-row>
    </div>
</template>

<script>
    import MTable from '@/components/table/Table'

    import echarts from 'echarts'
    export default {
        name: "Main",
        components: {MTable},
        data(){
            return {
                chart1: null,
                cData: [

                ],
                columns: [
                    {title: 'ID', dataIndex: 'id'},
                    {title: '任务名称', dataIndex: 'name'},
                    {title: '简要描述', dataIndex: 'remark'},
                    {title: '创建日期', dataIndex: 'createTime'},
                    {title: '操作', slot: 'action', fixed: 'right'}
                ],
                taskData: [
                    {id: 1, name: '吃饭了', remark: '小明回家吃饭', createTime: '2019-06-15 23:00'}
                ]
            }
        },
        mounted() {
            this.initChart1();
            this.initChart2();
        },
        methods: {
            initChart1(){
                this.chart1 = echarts.init(document.getElementById('chart1'));
                this.chart1.setOption({
                    title: {
                        text: '品类销量统计'
                    },
                    tooltip: {},
                    xAxis: {
                        data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子']
                    },
                    yAxis: {},
                    series: [{
                        name: '销量',
                        type: 'bar',
                        data: [5, 20, 36, 10, 10, 20]
                    }]
                });
            },
            initChart2(){
                this.chart1 = echarts.init(document.getElementById('chart2'));
                this.chart1.setOption({
                    title: {
                        text: '月销量趋势分析'
                    },
                    tooltip: {},
                    xAxis: {
                        data: ['一月份', '二月份', '三月份', '四月份', '五月份', '六月份']
                    },
                    yAxis: {},
                    series: [{
                        name: '销量',
                        type: 'line',
                        data: [5, 20, 36, 10, 10, 20]
                    }]
                });
            }
        }
    }
</script>

<style scoped lang="less">
    .c-box {
        width: 100%;
        height: 100px;
        text-align: center;
        border: 1px solid #e1eff8;
        border-radius: 4px;
        .c-count {
            height: 70px;
            line-height: 70px;
            font-weight: bold;
            font-size: 32px;
        }
        .c-title {
            font-size: 16px;
            color: #fff;
            height: 28px;
            line-height: 28px;
        }
    }
</style>