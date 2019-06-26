<template>
    <div class="rate-setting">
        <a-form>
            <a-form-item label="默认值">
                <a-input-number v-model="config.value"></a-input-number>
            </a-form-item>
            <a-form-item label="星星个数">
                <a-input-number v-model="config.count"></a-input-number>
            </a-form-item>
            <a-form-item label="允许点击时清除">
                <a-switch v-model="config.allowClear"></a-switch>
            </a-form-item>
            <a-form-item label="允许半选">
                <a-switch v-model="config.allowHalf"></a-switch>
            </a-form-item>
        </a-form>
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
        }
    }
</script>

<style scoped>

</style>