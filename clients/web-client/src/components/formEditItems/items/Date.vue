<template>
    <div class="date-input">
        <div class="start-date">
            <a-month-picker v-if="config.type == 'month'" v-model="config.value"></a-month-picker>
            <a-week-picker v-else-if="config.type == 'week'" v-model="config.value"></a-week-picker>
            <a-time-picker v-else-if="config.type == 'time'" v-model="config.value" :format="'HH:mm:ss'"></a-time-picker>
            <a-date-picker v-else v-model="config.value" :format="formatter" :showTime="config.showTime"></a-date-picker>
        </div>
        <div v-if="config.range" class="split">~</div>
        <div v-if="config.range" class="end-date">
            <a-month-picker v-if="config.type == 'month'" v-model="config.value"></a-month-picker>
            <a-week-picker v-else-if="config.type == 'week'" v-model="config.value"></a-week-picker>
            <a-time-picker v-else-if="config.type == 'time'" v-model="config.value" :format="'HH:mm:ss'"></a-time-picker>
            <a-date-picker v-else v-model="config.value" :format="formatter" :showTime="config.showTime"></a-date-picker>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Date",
        props: {
            value: Object
        },
        model: {
            prop: 'value',
            event: 'change'
        },
        data(){
            return {
                config: this.value
            }
        },
        computed: {
            formatter(){
                if(this.config.showTime) {
                    return 'YYYY-MM-DD HH:mm:ss'
                }else{
                    return 'YYYY-MM-DD';
                }
            }
        },
        watch: {
            config: {
                handler(config){
                    this.$emit('change', config);
                },
                deep: true
            }
        }
    }
</script>

<style scoped lang="less">
    .date-input {
        padding-bottom: 20px;

        .start-date {
            float: left;
        }
        .split {
            float: left;
            margin-left: 15px;
        }
        .end-date {
            float: left;
            margin-left: 15px;
        }
    }
</style>