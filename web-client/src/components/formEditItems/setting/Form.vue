<template>
    <div class="title-setting">
        <a-form>
            <a-form-item label="标题颜色">
                <color-picker v-model="config.titleColor"></color-picker>
            </a-form-item>
        </a-form>
    </div>
</template>

<script>
    import ColorPicker from '@/components/form/ColorPicker'
    export default {
        name: "Form",
        components: {ColorPicker},
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