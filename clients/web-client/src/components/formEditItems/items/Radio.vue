<template>
    <div class="input-radio">
        <a-radio-group v-model="config.value">
            <div v-for="option in config.options" :style="{marginBottom: '10px'}">
                <a-radio :value="option.value">
                    {{option.label}}
                    <a-input v-if="option.inputable" :style="{ width: '90%', marginLeft: 10 }" v-model="option.text"></a-input>
                </a-radio>
            </div>
        </a-radio-group>
    </div>
</template>

<script>
    export default {
        name: "Radio",
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
                    this.$emit('change', config)
                },
                deep: true
            }
        }
    }
</script>

<style scoped>

</style>