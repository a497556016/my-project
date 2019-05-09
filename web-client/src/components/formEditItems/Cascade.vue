<template>
    <div class="cascade-input">
        <a-cascader :placeholder="config.placeholder" :options="config.options"></a-cascader>
    </div>
</template>

<script>
    export default {
        name: "Cascade",
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