<template>
    <div style="margin-bottom: 15px;">
        <a-form
                :form="form"
                @submit="handleSearch"
                :layout="'inline'"
        >
            <a-form-item v-for="item in items" :label="item.label">
                <a-input v-if="item.component == 'a-input'" v-decorator="[item.field, {rules: item.rules || []}]" :placeholder="item.placeholder"></a-input>
                <a-select v-else-if="item.component == 'a-select'" style="width: 160px;" v-decorator="[item.field, {rules: item.rules || []}]" :placeholder="item.placeholder">
                    <a-select-option v-for="data in item.data" :value="data.value">{{data.name}}</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item>
                <a-button type="primary" html-type="submit">搜索</a-button>
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
                form: this.$form.createForm(this),
                labelCol: { span: 4 },
                wrapperCol: { span: 14 },
            }
        },
        methods: {
            handleSearch(e){
                e.preventDefault();
                this.form.validateFields((error, values) => {
                    // console.log('error', error);
                    // console.log('Received values of form: ', values);
                    if(!error){
                        this.$emit('submit', values);
                    }
                });
            },
            handleReset () {
                this.form.resetFields();
            },
        },
    }
</script>

<style scoped>

</style>