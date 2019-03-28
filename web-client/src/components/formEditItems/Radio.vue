<template>
    <div class="radio">
        <div class="edit-actions" :style="{display: editActionsVisible?'inline-block':'none', left: editActionPosition.left+'px', top: editActionPosition.top+'px'}">
            <div class="action" @click="addInput"><a-icon type="plus"></a-icon>&nbsp;添加输入框</div>
        </div>
        <item-actions :visible="!editActionsVisible&&itemActionsVisible" :position="itemActionsPosition" @mouseleave="onMoveOut" @handler="actionHandler"></item-actions>
        <div class="options">
            <div class="item" v-for="(item, index) in options" style="display: flex">
                <div style="width:30px;height: 36px;line-height: 36px;">
                    <input type="radio" :name="'tt'+seq"/>
                </div>
                <div style="flex: 1;">
                    <input class="input" type="text" style="float: left" v-model="item.label" @mouseover="onMoveIn($event, index)" @mouseleave="onMoveOut" @click="onInputClick($event, index)"/>
                    <input v-if="item.editable" class="input more-input" type="text" />
                </div>
            </div>
        </div>

        <a-modal title="添加输入框" v-model="addInputModalVisible" @ok="addInputModalVisible = false">
            <a-checkbox @change="addInputChange" :checked="!index||options[index].editable">是否添加一个输入框</a-checkbox>
        </a-modal>
    </div>
</template>

<script>
    import ItemActions from "./ItemActions";
    export default {
        name: "Radio",
        components: {ItemActions},
        props: {
            seq: Number,
            option: Object
        },
        model: {
            prop: 'option',
            event: 'change'
        },
        data(){
            return {
                radioStyle: {
                    display: 'block',
                    height: '30px',
                    lineHeight: '30px',
                },
                options: this.option.options,
                itemActionsVisible: false,
                itemActionsPosition: {
                    left: 0,
                    top: 0
                },

                editActionsVisible: false,
                editActionPosition: {
                    left: 0,
                    top: 0
                },

                addInputModalVisible: false,

                target: null,
                index: null
            }
        },
        watch: {
            options(v){
                //设置每一项的分值
                v.forEach((option, index) => option.value = index);
                this.$emit('change', {
                    options: v
                });
            }
        },
        methods: {
            onInputClick(e, index){
                this.target = e.target;
                this.index = index;

                this.editActionPosition = {
                    left: this.target.offsetLeft + this.target.offsetWidth - 200,
                    top: this.target.offsetTop - 36
                }

                this.editActionsVisible = true;
                this.itemActionsVisible = false;
            },
            addInput(){
                this.addInputModalVisible = true;
            },
            addInputChange(e){
                this.options[this.index].editable = e.target.checked;
            },
            onMoveIn(e, index){
                // console.log(e, index);

                this.target = e.target;
                this.index = index;

                this.itemActionsPosition = {
                    left: this.target.offsetLeft + this.target.offsetWidth - 145,
                    top: this.target.offsetTop - 29
                }

                this.target.style.border = '1px solid #ff4d4f';
                this.itemActionsVisible = true;
            },
            onMoveOut(e){

                if(!e.toElement || (!e.toElement.classList.contains('item-actions') && !e.toElement.classList.contains('action'))) {
                    this.target.style.border = '0';
                    this.itemActionsVisible = false;
                    this.editActionsVisible = false;
                }

                // console.log(e);
            },
            actionHandler(type) {
                switch (type) {
                    default: break;
                    case 'plus':
                        this.options.splice(this.index+1, 0, {label: '选项'+(this.options.length), editable: false, value: this.options.length})
                        break;
                    case 'up': {
                        const option = this.options.splice(this.index - 1, 1);
                        this.options.splice(this.index, 0, option[0]);
                        break;
                    }
                    case 'down': {
                        let removeIndex = this.index + 1;
                        if(removeIndex == this.options.length){
                            removeIndex = 0;
                        }
                        const option = this.options.splice(removeIndex, 1);
                        this.options.splice(this.index, 0, option[0]);
                        break;
                    }
                    case 'delete': {
                        this.options.splice(this.index, 1);
                        break;
                    }
                }
            }
        }
    }
</script>

<style scoped lang="less">
    @labelWidth: 60px;

    input {
        border: 0;
        padding: 0 10px;
        width: 100%;
        height: 16px;
    }
    .more-input{
        border: 1px solid #cccccc;
    }

    .radio {

        .options {
            margin-top: 15px;
            .item {
                padding: 0 15px;
                margin-bottom: 10px;
                .input {
                    height: 30px;
                    line-height: 30px;
                }
            }
        }
    }

    .edit-actions {
        background: #ffffff;
        border: 1px solid #cccccc;
        height: 36px;
        line-height: 36px;
        padding-right: 5px;
        position: absolute;
        .action {
            width: 100px;
            float: left;
            margin-left: 5px;
            cursor: pointer;
        }
    }
</style>