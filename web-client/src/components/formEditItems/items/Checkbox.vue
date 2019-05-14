<template>
    <div class="checkbox">
        <a-checkbox-group>
            <a-row v-for="option in options" :style="{marginBottom: '10px'}">
                <a-checkbox :value="option.value">{{option.label}}</a-checkbox>
            </a-row>
        </a-checkbox-group>
    </div>
</template>

<script>
    export default {
        name: "Checkbox",
        props: {
            config: Object
        },
        model: {
            prop: 'config',
            event: 'change'
        },
        data(){
            return {
                options: this.config.options
            }
        }
    }
</script>

<style scoped lang="less">
    .checkbox {

    }
</style>