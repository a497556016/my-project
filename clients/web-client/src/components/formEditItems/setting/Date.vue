<template>
    <div class="date-setting">
        <a-form>
            <a-form-item label="日期类型">
                <a-select v-model="config.type">
                    <a-select-option v-for="type in dateTypes" :value="type[1]">{{type[0]}}</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="是否可选时间" v-if="config.type == 'date'">
                <a-switch v-model="config.showTime"></a-switch>
            </a-form-item>
            <a-form-item label="区间日期">
                <a-switch v-model="config.range"></a-switch>
            </a-form-item>
        </a-form>
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
                config: this.value,
                dateTypes: [['日期','date'],['月份','month'],['周','week'],['时间','time']]
            }
        },
        watch: {
            config: {
                handler(config) {
                    this.$emit('change', config);
                },
                deep: true
            }
        }
    }
</script>

<style scoped>

</style>