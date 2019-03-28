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
        <div class="right-content">
            <div class="input-item title">
                <input type="text" placeholder="请输入标题" />
            </div>
            <div class="input-item">
                <input type="text" value="感谢您能抽时间参与本次问卷，您的意见和建议就是我们前行的动力！" />
            </div>
            <div v-if="inputItems.length == 0" class="drag-content" @drop="dropHandler" @dragover="allowDrop">
                从左边控件选择控件拖动到该区域
            </div>
            <div v-else class="input-item" v-for="(item, index) in inputItems" :data-index="index" @drop="dropHandler" @dragover="allowDrop" @dragleave="dragLeaveHandler">
                <question :seq="index+1" :placeholder="item.name" v-model="item.question"></question>
                <component :is="item.component" v-model="item.meta"></component>
                <a-divider/>
                <item-footer v-model="inputItems[index]" :index="index" @copy="inputItems.splice(index+1, 0, item)" @remove="inputItems.splice(index, 1)" @move="moveItem(index)"></item-footer>

                <div v-if="item.disabled" class="disabled"></div>
            </div>
            <div>
                <a-button @click="save" type="primary">保存</a-button>
            </div>
        </div>
    </div>
</template>

<script>
    import Question from '@/components/formEditItems/Question'
    import Radio from '@/components/formEditItems/Radio'
    import MSelect from '@/components/formEditItems/Select'
    import ItemFooter from "@/components/formEditItems/ItemFooter";

    export default {
        name: "Create",
        components: {ItemFooter, Question, Radio, MSelect},
        data(){
            return {
                formItems: [
                    {
                        name: '单选题', component: 'radio', question: '请选择以下一项', meta: {
                            options: [
                                {label: '选项', value: 0, editable: false},
                                {label: '其他', value: 1, editable: true}
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
                        name: '多选题', component: 'checkbox', meta: {

                        },
                        disabled: false
                    },
                    {
                        name: '填空题', component: 'input', meta: {

                        },
                        disabled: false
                    },
                    {
                        name: '多项填空题', component: 'multiInput', meta: {

                        },
                        disabled: false
                    },
                    {
                        name: '评分题', component: 'rate', meta: {

                        },
                        disabled: false
                    },
                    {
                        name: '日期选择题', component: 'date', meta: {

                        },
                        disabled: false
                    },
                    {
                        name: '城市选择题', component: 'city', meta: {

                        },
                        disabled: false
                    },
                    {
                        name: '关联选择题', component: 'cascade', meta: {

                        },
                        disabled: false
                    },
                    {
                        name: '描述说明', component: 'description', meta: {

                        },
                        disabled: false
                    }
                ],
                inputItems: [],
                dragItem: null,
                targetEl: null,

                onItemTop: false
            }
        },
        methods: {
            dragBegin(e){
                console.log(e);
                const component = e.target.dataset.component;
                this.dragItem = this.formItems.find(item => item.component == component);
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

                if(this.inputItems.length == 0) {
                    this.inputItems.push(this.dragItem);
                }else{
                    let index = this.targetEl.dataset.index;
                    if(!this.onItemTop){
                        index = parseInt(index) + 1;
                    }
                    // console.log(index);
                    this.inputItems.splice(index, 0, this.dragItem);
                }
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
                this.targetEl = e.path.find(el => el.className == 'input-item');
                // console.log(this.targetEl)
                if(this.isTop(e)){
                    this.targetEl.style.borderTop = '2px solid #ff0000';
                    this.targetEl.style.borderBottom = '0';
                }else{
                    this.targetEl.style.borderBottom = '2px solid #ff0000';
                    this.targetEl.style.borderTop = '0';
                }
            },
            dragLeaveHandler(e){
                if(this.targetEl) {
                    this.targetEl.style.borderTop = '0';
                    this.targetEl.style.borderBottom = '0';
                }
            },
            isTop(e){
                const srcY = e.y;
                const toY = this.targetEl.offsetTop + this.targetEl.offsetParent.offsetTop - this.targetEl.offsetParent.scrollTop;//取到相对body的高度
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
            save(){
                console.log(this.inputItems)
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
            flex: 1;

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
    }
</style>