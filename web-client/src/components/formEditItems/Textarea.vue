<template>
    <div class="textarea-input">
        <a-textarea :placeholder="config.placeholder"></a-textarea>
    </div>
</template>

<script>
    export default {
        name: "Textarea",
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