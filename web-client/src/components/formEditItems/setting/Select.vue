<template>
    <div class="select-setting">
        <a-button @click="addItem">添加一行</a-button>
        <div class="grid">
            <a-row>
                <a-col :span="18">文本</a-col>
                <a-col :span="6">值</a-col>
            </a-row>
            <a-row v-for="option in config.options" :style="{marginTop: '5px'}">
                <a-col :span="18">
                    <a-input v-model="option.label"></a-input>
                </a-col>
                <a-col :span="6">
                    <a-input v-model="option.value"></a-input>
                </a-col>
            </a-row>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Select",
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
        methods: {
            addItem(){
                const value = _.maxBy(this.config.options, 'value').value + 1;
                this.config.options.push({label: '新的选项', value: value});
                this.$emit('change', this.config);
            }
        }
    }
</script>

<style scoped>
    .grid{
        padding: 5px 0;
    }
</style>