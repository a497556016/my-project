<template>
    <div style="padding: 25px">
        <a-row type="flex" justify="space-between" align="middle">
            <a-col :span="4" class="c-box">
                <div class="number">{{csData.fileUsedSpace}} MB</div>
                <div>文件已占用空间</div>
            </a-col>
            <a-col :span="4" class="c-box">
                <div class="number" :style="{color: csData.diskLeftSpace<10?'#f00':''}">{{csData.diskLeftSpace}} GB</div>
                <div>剩余可用空间</div>
            </a-col>
            <a-col :span="4" class="c-box">
                <div class="number">{{csData.todayUploadTimes}}</div>
                <div>今日上传次数</div>
            </a-col>
            <a-col :span="4" class="c-box">
                <div class="number">{{csData.todayUploadSize}} MB</div>
                <div>今日上传容量</div>
            </a-col>
            <a-col :span="4" class="c-box">
                <div class="number">{{csData.todayDownloadTimes}}</div>
                <div>今日访问次数</div>
            </a-col>
        </a-row>
        <a-row style="margin-top: 50px;">
            <a-col :span="12">
                <div id="uploadTimesChart" style="height: 300px;"></div>
            </a-col>
            <a-col :span="12">
                <div id="downloadTimesChart" style="height: 300px;"></div>
            </a-col>
        </a-row>
    </div>
</template>

<script>
    import {mapState, mapMutations, mapActions} from 'vuex'
    import echarts from 'echarts'

    import {fileMain} from '../../store/types'
    export default {
        name: "Main",
        data(){
            return {
                uploadTimesChart: null,
                downloadTimesChart: null
            }
        },
        computed: {
            ...mapState({
                csData: state => state.fileMain.csData
            })
        },
        mounted() {
            this.initUploadTimesChart();
            this.initDownloadTimesChart();
            this.loadClassifiedStatisticalData();
        },
        methods: {
            ...mapActions({
                loadClassifiedStatisticalData: fileMain.actions.LOAD_CLASSIFIED_STATISTICAL_DATA
            }),
            initUploadTimesChart(){
                this.uploadTimesChart = echarts.init(document.getElementById('uploadTimesChart'));

                this.uploadTimesChart.setOption({
                    title: {
                        text: '每日上传次数分析'
                    },
                    tooltip: {},
                    xAxis: {
                        data: ['19-03-21', '19-03-22', '19-03-23', '19-03-24', '19-03-25', '19-03-26']
                    },
                    yAxis: {},
                    series: [{
                        name: '上传次数',
                        type: 'line',
                        data: [5, 20, 36, 10, 10, 20]
                    }]
                })
            },
            initDownloadTimesChart(){
                this.downloadTimesChart = echarts.init(document.getElementById('downloadTimesChart'));

                this.downloadTimesChart.setOption({
                    title: {
                        text: '每日访问次数分析'
                    },
                    tooltip: {},
                    xAxis: {
                        data: ['19-03-21', '19-03-22', '19-03-23', '19-03-24', '19-03-25', '19-03-26']
                    },
                    yAxis: {},
                    series: [{
                        name: '访问次数',
                        type: 'line',
                        data: [56, 95, 36, 86, 10, 1]
                    }]
                })
            }
        }
    }
</script>

<style scoped lang="less">
    .c-box {
        text-align: center;

        .number {
            font-size: 23px;
            font-weight: bold;
        }
    }
</style>