<template>
    <div class="rate">
        <a-rate v-model="config.value" :count="config.count" :allowClear="config.allowClear" :allowHalf="config.allowHalf"></a-rate>
    </div>
</template>

<script>
    export default {
        name: "Rate",
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

<style scoped>

</style>