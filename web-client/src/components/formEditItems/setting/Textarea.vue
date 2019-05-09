<template>
    <div class="textarea-setting">
        <a-form>
            <a-form-item label="提示语">
                <a-input v-model="config.placeholder"></a-input>
            </a-form-item>
            <a-form-item label="限制字数">
                <a-input v-model="config.max"></a-input>
            </a-form-item>
        </a-form>
    </div>
</template>

<script>
    import AFormItem from "ant-design-vue/es/form/FormItem";
    export default {
        name: "Textarea",
        components: {AFormItem},
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