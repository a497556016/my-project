<template>
    <div class="setting-radio">
        <a-button @click="addItem">添加一行</a-button>
        <div class="grid">
            <a-row>
                <a-col :span="15" :style="{textAlign: 'center'}">文本</a-col>
                <!--<a-col :span="3" :style="{textAlign: 'center'}">值</a-col>-->
                <a-col :span="6" :style="{textAlign: 'center'}">可输入</a-col>
                <a-col :span="3" :style="{textAlign: 'center'}">删除</a-col>
            </a-row>
            <a-row :gutter="5" v-for="(option, index) in config.options" :style="{marginTop: '5px'}">
                <a-col :span="15" :style="{textAlign: 'center'}">
                    <a-input v-model="option.label" placeholder="请输入选项名称"></a-input>
                </a-col>
                <!--<a-col :span="3" :style="{textAlign: 'center'}">-->
                    <!--<a-input-number v-model="option.value" :style="{width: '50px'}"></a-input-number>-->
                <!--</a-col>-->
                <a-col :span="6" :style="{textAlign: 'center'}">
                    <a-switch v-model="option.inputable"></a-switch>
                </a-col>
                <a-col :span="3" :style="{textAlign: 'center'}">
                    <a-popconfirm title="确认要删除吗？" @confirm="removeItem(index)">
                        <a-icon slot="icon" type="question-circle-o" style="color: red" />
                        <a-button type="danger" size="small"><a-icon type="delete"></a-icon></a-button>
                    </a-popconfirm>
                </a-col>
            </a-row>
        </div>
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
        methods: {
            addItem(){
                const maxOption = _.maxBy(this.config.options, 'value');
                const value = (maxOption?maxOption.value:0) + 1;
                this.config.options.push({label: '', value: value});
                this.$emit('change', this.config);
            },
            removeItem(index){
                this.config.options.splice(index, 1);
                // this.config.options.forEach((option, i) => option.value = i);
            }
        }
    }
</script>

<style scoped lang="less">
    .setting-radio {
        .grid{
            margin: 5px 0;
            padding: 3px;
            border: 1px solid #dedede;
        }
    }

</style>