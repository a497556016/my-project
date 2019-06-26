<template>
    <div class="cascade-setting">
        <a-button-group>
            <a-button @click="addOption = {};addModal = true;">添加子节点</a-button>
            <a-button type="primary" @click="editNode">编辑</a-button>
            <a-button type="danger" @click="removeNode">删除</a-button>
        </a-button-group>
        <a-form>
            <a-form-item label="级联选择数据">
                <a-tree
                        autoExpandParent
                        showLine
                        draggable
                        :treeData="treeData"
                        @drop="onDrop"
                        @select="onSelect"
                ></a-tree>
            </a-form-item>
        </a-form>

        <a-modal title="编辑节点" v-model="editModal" @ok="onEdit">
            <a-form>
                <a-form-item label="名称">
                    <a-input v-model="selectedOption.label"></a-input>
                </a-form-item>
                <a-form-item label="值">
                    <a-input v-model="selectedOption.value" readonly></a-input>
                </a-form-item>
            </a-form>
        </a-modal>

        <a-modal title="添加节点" v-model="addModal" @ok="addChildNode">
            <a-input v-model="addOption.label"></a-input>
        </a-modal>
    </div>
</template>

<script>
    import AFormItem from "ant-design-vue/es/form/FormItem";
    export default {
        name: "Cascade",
        components: {AFormItem},
        props: {
            value: Object
        },
        model: {
            prop: 'value',
            event: 'change'
        },
        data(){
            return {
                config: this.value,

                selectedNode: null,
                selectedOption: {},
                selectedIndex: null,
                selectedArr: null,

                editModal: false,

                addModal: false,
                addOption: {}
            }
        },
        computed: {
            treeData(){
                return this.convertData(this.config.options);
            }
        },
        watch: {
            config: {
                handler(config) {
                    this.$emit('change', config);
                },
                deep: true
            }
        },
        methods: {
            convertData(options){
                return options.map(option => {
                    const result = {
                        title: option.label,
                        key: option.value,
                        slots: {

                        }
                    }
                    if(option.children) {
                        result.children = this.convertData(option.children);
                    }
                    return result;
                })
            },
            onSelect(selectedKeys, e) {
                console.log(selectedKeys, e);
                this.selectedNode = e.node;
                this.findByKey(this.config.options, this.selectedNode.eventKey, (item, index, arr) => {
                    this.selectedOption = item;
                    this.selectedIndex = index;
                    this.selectedArr = arr;
                });
            },
            addChildNode() {
                if(!this.addOption.label){
                    return;
                }
                //关闭弹窗
                this.addModal = false;

                if(!this.selectedNode) {
                    const num = (_.max(this.config.options.map((option) => {
                        const arr = option.value.split('-');
                        return parseInt(arr[arr.length-1]);
                    }))||0)+1;
                    this.addOption.value = ''+num;

                    this.config.options.push(_.cloneDeep(this.addOption))
                }else {
                    this.selectedOption.children = this.selectedOption.children || [];

                    const num = (_.max(this.selectedOption.children.map((option) => {
                        const arr = option.value.split('-');
                        return parseInt(arr[arr.length-1]);
                    }))||0)+1;
                    this.addOption.value = this.selectedOption.value+'-'+num;

                    this.selectedOption.children.push(_.cloneDeep(this.addOption));

                    this.config.options = [...this.config.options];
                }
            },
            editNode() {
                if(!this.selectedNode) {
                    this.$message.warning('请选择一个节点！');
                }else {
                    this.editModal = true;
                }
            },
            onEdit() {
                this.config.options = [...this.config.options];
                this.editModal = false;
            },
            removeNode() {
                if(!this.selectedNode) {
                    this.$message.warning('请选择一个节点！');
                }else {
                    this.selectedArr.splice(this.selectedIndex, 1);
                    this.config.options = [...this.config.options];

                    this.selectedNode = null;
                    /*this.selectedIndex = null;
                    this.selectedArr = null;
                    this.selectedOption = null;*/
                }
            },
            findByKey(data, key, callback) {
                data.forEach((item, index, arr) => {
                    if (item.value === key) {
                        return callback(item, index, arr)
                    }
                    if (item.children) {
                        return this.findByKey(item.children, key, callback)
                    }
                })
            },
            onDrop(info) {
                console.log(info)
                const dropKey = info.node.eventKey
                const dragKey = info.dragNode.eventKey
                const dropPos = info.node.pos.split('-')
                const dropPosition = info.dropPosition - Number(dropPos[dropPos.length - 1])
                /*const loop = (data, key, callback) => {
                    data.forEach((item, index, arr) => {
                        if (item.value === key) {
                            return callback(item, index, arr)
                        }
                        if (item.children) {
                            return loop(item.children, key, callback)
                        }
                    })
                }*/
                const data = [...this.config.options]

                // Find dragObject
                let dragObj
                this.findByKey(data, dragKey, (item, index, arr) => {
                    arr.splice(index, 1)
                    dragObj = item
                })
                if (!info.dropToGap) {
                    // Drop on the content
                    this.findByKey(data, dropKey, (item) => {
                        item.children = item.children || [];
                        // where to insert 示例添加到尾部，可以是随意位置
                        item.children.push(dragObj);
                    });
                } else if (
                    (info.node.children || []).length > 0 // Has children
                    && info.node.expanded // Is expanded
                    && dropPosition === 1 // On the bottom gap
                ) {
                    this.findByKey(data, dropKey, (item) => {
                        item.children = item.children || [];
                        // where to insert 示例添加到尾部，可以是随意位置
                        item.children.unshift(dragObj);
                    });
                } else {
                    let ar;
                    let i;
                    this.findByKey(data, dropKey, (item, index, arr) => {
                        ar = arr;
                        i = index;
                    });
                    if (dropPosition === -1) {
                        ar.splice(i, 0, dragObj);
                    } else {
                        ar.splice(i + 1, 0, dragObj);
                    }
                }
                this.config.options = data;

                console.log(this.config.options)
            }
        }
    }
</script>

<style scoped>

</style>