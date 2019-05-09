<template>
    <div class="item-footer">
        <div class="action" @click="collect">
            <a-icon type="star" /> 收藏
        </div>
        <div class="action" @click="copy">
            <a-icon type="copy" /> 复制
        </div>
        <div class="action" @click="remove">
            <a-icon type="delete" /> 删除
        </div>
        <div class="action" style="cursor: move;" @dragstart="dragstart"  @drag="drag" @dragend="dragend" draggable="true">
            <a-icon type="plus-square" /> 移动
        </div>
        <div class="action" style="z-index: 2;" @click="toggleHide">
            <a-icon :type="item.disabled?'eye':'eye-invisible'" /> {{item.disabled?'显示':'隐藏'}}
        </div>
        <div class="action" @click="setting">
            <a-icon type="setting" /> 设置
        </div>
    </div>
</template>

<script>
    export default {
        name: "ItemFooter",
        props: {
            item: {
                type: Object
            },
            index: Number
        },
        data(){
            return {
                dragMsgShow: false,

                dragItem: null,

                disabled: this.item.disabled
            }
        },
        methods: {
            collect(){
                this.$message.error('哎呀呀，这个功能还没有开发呢~~');
            },
            copy(){
                this.$emit('copy')
            },
            remove(){
                const that = this;
                this.$confirm({
                    title: '你确认要删除吗？',
                    onOk(){
                        that.$emit('remove');
                    }
                });
            },
            dragstart(e){
                this.moveIcon = e.target.innerHTML;
                e.target.innerHTML = '问题' + (this.index+1) + ':' + this.item.name;
            },
            drag(e){
                // e.preventDefault();
                console.log(e)
                this.dragItem = e.path.find(el => el.className.startsWith('input-item'));
                if(this.dragItem) {
                    this.dragItem.style.display = 'none';
                }
            },

            dragend(e){
                e.preventDefault();
                console.log('moveend')
                this.dragItem.style.display = 'block';
                this.dragItem = null;

                e.target.innerHTML = this.moveIcon;

                this.$emit('move')
            },
            toggleHide(){
                this.disabled = !this.disabled;
                this.$emit('hide');
            },

            setting(){
                this.$emit('setting');
            }
        }
    }
</script>

<style scoped lang="less">
    .item-footer{
        display: flex;
        justify-content: flex-end;
        font-size: 15px;
        .action {
            cursor: pointer;
            margin-right: 20px;
        }
        margin-bottom: 10px;
    }
</style>