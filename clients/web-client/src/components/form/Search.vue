<template>
    <div style="margin-bottom: 15px;">
        <a-form
                :layout="'inline'"
        >
            <a-form-item v-for="item in items" :label="item.label">
                <a-select v-if="item.component == 'a-select'" style="width: 160px;" v-model="values[item.field]" :placeholder="item.placeholder">
                    <a-select-option v-for="data in item.data" :value="data.value">{{data.name}}</a-select-option>
                </a-select>
                <component v-else :is="item.component" v-model="values[item.field]" :placeholder="item.placeholder"></component>
            </a-form-item>
            <a-form-item>
                <a-button type="primary" @click="handleSearch">搜索</a-button>
                <a-button @click="handleReset" :style="{marginLeft: '6px'}">重置</a-button>
            </a-form-item>
        </a-form>
    </div>
</template>

<script>
    export default {
        name: "Search",
        props: {
            items: {
                type: Array,
                default(){
                    return [
                        {component: 'a-input', field: 'username', placeholder: '请输入用户名', rules: [{required: true, message: '请输入用户名!'}]},
                        {component: 'a-select', field: 'area', placeholder: '请选择区域', data: [{name: 'item1', value: 1}]}
                    ]
                }
            }
        },
        data(){
            return {
                expand: false,
                values: {},
                labelCol: { span: 4 },
                wrapperCol: { span: 14 },
            }
        },
        methods: {
            handleSearch(){
                this.$emit('submit', this.values);
            },
            handleReset () {
                this.values = {};
            },
            getValues(){
                return this.values;
            }
        },
    }
</script>

<style scoped>

</style>