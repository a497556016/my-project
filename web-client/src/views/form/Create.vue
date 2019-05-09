<template>
    <div class="create-form">
        <div class="left-content">
            <div class="title">表单控件</div>
            <div class="form-items">
                <div class="item" v-for="item in formItems" :data-component="item.component" draggable="true" @dragstart="dragBegin" @dragend="dragEnd">
                    {{item.name}}
                </div>
            </div>
        </div>
        <div class="right-content" :style="{height: contentHeight}">
            <div class="input-item title">
                <input type="text" placeholder="请输入标题" v-model="formData.title" />
            </div>
            <div class="input-item">
                <input type="text" placeholder="" v-model="formData.desc" />
            </div>
            <div v-if="inputItems.length == 0" class="drag-content" @drop="dropHandler" @dragover="allowDrop">
                从左边控件选择控件拖动到该区域
            </div>
            <div v-else :class="['input-item', (settingItem&&settingItem.index==index)?'item-bordered':'']" v-for="(item, index) in inputItems" :key="index" :data-index="index" @drop="dropHandler" @dragover="allowDrop" @dragleave="dragLeaveHandler">
                <question :seq="index+1" :placeholder="item.name" v-model="item.question"></question>
                <component :is="item.component" v-model="item.meta"></component>
                <a-divider/>
                <item-footer :item="item" :index="index" @copy="inputItems.splice(index+1, 0, item)" @remove="onRemove(index)" @move="moveItem(index)" @setting="configItem(index)" @hide="onHide(item)"></item-footer>

                <!-- 隐藏的遮罩层 -->
                <div v-if="item.disabled" class="disabled"></div>
            </div>
            <div class="input-item">
                <a-button @click="save" type="primary">保存</a-button>
            </div>
        </div>
        <div class="setting-content" v-if="settingItem">
            <div class="title">问题内容设置</div>
            <component :is="settingItem.component" v-model="settingItem.config"></component>
        </div>
    </div>
</template>

<script>
    import Question from '@/components/formEditItems/Question'
    import MRadio from '@/components/formEditItems/Radio'
    import MSelect from '@/components/formEditItems/Select'
    import MCheckbox from '@/components/formEditItems/Checkbox'
    import MInput from '@/components/formEditItems/Input'
    import MRate from '@/components/formEditItems/Rate'
    import MDate from '@/components/formEditItems/Date'
    import MTextarea from '@/components/formEditItems/Textarea'
    import MCascade from '@/components/formEditItems/Cascade'

    import ItemFooter from "@/components/formEditItems/ItemFooter";

    import MSelectSetting from '@/components/formEditItems/setting/Select';
    import MCheckboxSetting from '@/components/formEditItems/setting/Checkbox';
    import MRateSetting from '@/components/formEditItems/setting/Rate';
    import MDateSetting from '@/components/formEditItems/setting/Date';
    import MTextareaSetting from '@/components/formEditItems/setting/Textarea';
    import MCascadeSetting from '@/components/formEditItems/setting/Cascade';

    import {formCreate} from '@/store/types'

    import {mapActions} from 'vuex'

    const defaultFormDesc = "感谢您能抽时间参与本次问卷，您的意见和建议就是我们前行的动力！";

    export default {
        name: "Create",
        components: {
            ItemFooter, Question, MRadio, MSelect, MCheckbox, MInput, MRate, MDate, MTextarea, MCascade,
            MSelectSetting, MCheckboxSetting, MRateSetting, MDateSetting, MTextareaSetting, MCascadeSetting
        },
        data(){
            return {
                formData: {
                    desc: defaultFormDesc
                },
                formItems: [
                    {
                        name: '单选题', component: 'm-radio', question: '请选择以下一项', meta: {
                            options: [
                                {label: '选项', value: 0, inputable: false},
                                {label: '其他', value: 1, inputable: true}
                            ]
                        },
                        disabled: false
                    },
                    {
                        name: '下拉选择题', component: 'm-select', question: '你钟意哪一项啊？', meta: {
                            options: [
                                {label: '选项', value: 0},
                                {label: '其他', value: 1}
                            ]
                        },
                        disabled: false
                    },
                    {
                        name: '多选题', component: 'm-checkbox', meta: {
                            options: [
                                {label: '选项1', value: 0},
                                {label: '选项2', value: 1},
                                {label: '选项3', value: 2},
                                {label: '其他', value: 3}
                            ],
                            maxNum: 0
                        },
                        disabled: false
                    },
                    {
                        name: '填空题', component: 'm-input', meta: {
                            placeholder: '请输入你的答案'
                        },
                        disabled: false
                    },
                    {
                        name: '评分题', component: 'm-rate', meta: {
                            value: 0, //默认值
                            count: 10, //星星个数
                            allowClear: false, //允许点击时清除
                            allowHalf: false //允许半选
                        },
                        disabled: false
                    },
                    {
                        name: '日期选择题', component: 'm-date', meta: {
                            showTime: false,
                            type: 'time', //date month week time
                            range: false
                        },
                        disabled: false
                    },
                    {
                        name: '城市选择题', component: 'm-city', meta: {

                        },
                        disabled: false
                    },
                    {
                        name: '关联选择题', component: 'm-cascade', meta: {
                            placeholder: '请选择',
                            options: [
                                {label: '选项一', value: '1'},
                                {
                                    label: '选项二', value: '2', children: [
                                        {label: '选项二子选项', value: '2-1'}
                                    ]
                                }
                            ]
                        },
                        disabled: false
                    },
                    {
                        name: '描述说明', component: 'm-textarea', meta: {
                            placeholder: '请在此填写说明文字',
                            max: 100
                        },
                        disabled: false
                    }
                ],
                inputItems: [],
                dragItem: null,
                targetEl: null,

                settingItem: null,

                onItemTop: false
            }
        },
        computed: {
            contentHeight(){
                return (window.innerHeight-220)+'px';
            }
        },
        methods: {
            ...mapActions({
                saveForm: formCreate.SAVE_FORM
            }),
            dragBegin(e){
                // console.log(e);
                const component = e.target.dataset.component;
                const finded = this.formItems.find(item => item.component == component);
                this.dragItem = _.cloneDeep(finded);
            },
            dragEnd(e){
                this.dragItem = null;
            },
            dropHandler(e){
                // console.log(e);
                this.dragLeaveHandler();

                console.log(this.dragItem)
                if(!this.dragItem){
                    return;
                }

                let index = 0;
                if(this.inputItems.length == 0) {
                    this.inputItems.push(this.dragItem);
                }else{
                    index = this.targetEl.dataset.index;
                    if(!this.onItemTop){
                        index = parseInt(index) + 1;
                    }
                    console.log(index);
                    this.inputItems.splice(index, 0, this.dragItem);
                }

                //打开设置
                this.settingItem = {index: index, component: this.dragItem.component+'-setting', config: this.dragItem.meta}
            },
            moveItem(index){
                let targetIndex = this.targetEl.dataset.index;

                console.log('移除：', index, targetIndex);

                const item = this.inputItems.splice(index, 1)[0];

                if(index < targetIndex) {
                    targetIndex--;
                }

                if(!this.onItemTop){
                    targetIndex = parseInt(targetIndex) + 1;
                }
                // console.log(index);
                this.inputItems.splice(targetIndex, 0, item);
            },
            allowDrop(e){
                e.preventDefault();
                console.log(e)
                if(e.target.classList.contains('drag-content')){
                    return;
                }
                this.targetEl = e.path.find(el => el.className.startsWith('input-item'));
                // console.log(this.targetEl)
                if(this.isTop(e)){
                    this.targetEl.classList.add('move-in-top');
                    this.targetEl.classList.remove('move-in-bottom');
                }else{
                    this.targetEl.classList.remove('move-in-top');
                    this.targetEl.classList.add('move-in-bottom');
                }
            },
            dragLeaveHandler(e){
                if(this.targetEl) {
                    this.targetEl.classList.remove('move-in-top');
                    this.targetEl.classList.remove('move-in-bottom');
                }
            },
            isTop(e){
                const srcY = e.y;
                const toY = this.targetEl.offsetTop + this.targetEl.offsetParent.offsetTop - this.targetEl.parentElement.scrollTop - this.targetEl.offsetParent.scrollTop;//取到相对body的高度
                const height = this.targetEl.offsetHeight;

                if(srcY - toY < height / 2) {
                    this.onItemTop = true;
                    // console.log('在上面', e, this.targetEl, toY, height);
                    return true;
                }else{
                    this.onItemTop = false;
                    // console.log('在下面', e, this.targetEl, toY, height);
                    return false;
                }
            },
            configItem(index) {
                const item = this.inputItems[index];

                this.settingItem = {index: index, component: item.component+'-setting', config: item.meta}
            },
            onHide(item) {
                item.disabled = !item.disabled;
            },
            onRemove(index) {
                this.inputItems.splice(index, 1);
                if(this.settingItem.index == index) {
                    this.settingItem = undefined;
                }
            },
            save(){
                console.log(this.inputItems);
                if(!this.formData.title){
                    this.$message.error('请输入表单标题！');
                    return;
                }
                if(this.inputItems.length==0){
                    this.$error({
                        content: '没有表单元素！'
                    })
                    return;
                }
                this.formData.formItems = this.inputItems;
                this.saveForm(this.formData).then(res => {
                    if(res.code == 1) {
                        this.$message.success("保存成功！");

                        //重置表单
                        this.reset();
                    }
                });;
            },
            reset() {
                this.formData = {
                    desc: defaultFormDesc
                }
                this.inputItems = [];
                this.settingItem = null;
            }
        }
    }
</script>

<style scoped lang="less">
    @bgColor: #e2e2e2;
    @red: #fe2222;
    @red-1: #ee7f48;
    @white: #ffffff;
    @lightGray: #f0f0f0;



    .create-form {
        background: @bgColor;
        padding: 15px;
        display: flex;
        width: 100%;
        min-height: 500px;

        .left-content {
            width: 150px;
            float: left;
            position: fixed;
            top: 165px;

            .title {
                height: 36px;
                line-height: 36px;
                padding: 0 15px;
                background: @white;
                border-bottom: 2px solid @red-1;
                font-size: 10px;
            }

            .form-items {
                /*text-align: center;*/
                background: @bgColor;

                .item {
                    cursor: move;
                    height: 36px;
                    line-height: 36px;
                    background: @white;
                    margin: 1px 0 1px 0;
                    padding: 0 15px;

                    &:hover {
                        margin: 1px 5px;
                        color: @red;
                    }

                    &:first-child {
                        border-top: 1px solid @bgColor;
                    }

                    &:last-child {
                        border-bottom: 1px solid @bgColor;
                    }
                }
            }
        }

        .right-content {
            float: left;
            margin-left: 175px;
            margin-right: 305px;
            flex: 1;
            overflow-y: auto;

            .input-item{
                position: relative;
                background: @white;
                padding: 10px 15px;
                &:not(:first-child) {
                    margin: 10px 0;
                }
                input {
                    border: 0;
                    padding: 0 5px;
                    &:hover {
                        border: 1px solid @red-1;
                        background: @lightGray;
                    }
                    width: 100%;
                }

                .disabled {
                    position: absolute;
                    width: 100%;
                    height: 100%;
                    top: 0;
                    left: 0;
                    background: hsla(0,0%,88%,.5);
                }
            }
            .item-bordered {
                border: 1px dashed #4e7cd0;
            }
            .move-in-top {
                border-top: 2px solid #ff0000;
            }
            .move-in-bottom {
                border-bottom: 2px solid #ff0000;
            }
            .title {
                padding: 35px 5px;
                input {
                    font-size: 28px;
                    font-weight: bold;
                    text-align: center;
                }
            }
            .drag-content {
                height: 80px;
                line-height: 80px;
                text-align: center;
                font-size: 28px;
                background: @lightGray;
                border: 1px dashed @red;
                color: @bgColor;
            }
        }

        .setting-content {
            float: left;
            width: 280px;
            background: #fff;
            padding: 15px;
            border: 1px dashed #4e7cd0;
            position: fixed;
            right: 50px;
            .title {
                font-weight: bold;
                margin-bottom: 15px;
            }
        }
    }
</style>