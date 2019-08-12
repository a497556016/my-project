<template>
    <div ref="editor" class="h5-editor" :style="{height: fullHeight}">
        <div class="modal" v-if="showModal"></div>
        <div ref="content" class="content" contenteditable="true" :placeholder="placeholder" @focus="onFocus($event)" @input="onInput($event)">

        </div>
        <div class="toolbar">
            <div v-for="action in actions" class="item" @click="insert(action.name)">
                <i :class="action.icon"></i>
            </div>
        </div>
    </div>
</template>

<script>

    const actions = [
        {name: 'image', icon: 'fa fa-image'},
        {name: 'list', icon: 'fa fa-list-ul'},
        {name: 'paragraph', icon: 'fa fa-paragraph'},
        {name: 'center', icon: 'fa fa-align-center'},
        {name: 'left', icon: 'fa fa-align-left'},
        {name: 'right', icon: 'fa fa-align-right'}
    ]

    export default {
        name: "H5Editor",
        props: {
            height: Number,
            placeholder: {
                type: String,
                default: '在这里输入内容'
            },
            remote: {
                type: Boolean,
                default: false
            },
            url: String,
            onsuccess: Function
        },
        data(){
            return {
                actions,
                showModal: false,
                showImgSelect: false
            }
        },
        computed: {
            fullHeight(){
                return (this.height||window.innerHeight)+'px';
            }
        },
        mounted(){
            document.addEventListener('selectionchange',() => {
                this.getCursor();
            });
        },
        methods: {
            onFocus(e){
                // console.log(e)
                this.defaultHtml(e.target);
            },
            onInput(e){
                // console.log(e);
                this.defaultHtml(e.target);
            },
            defaultHtml(el){
                if(el.innerHTML == '') {
                    el.innerHTML = `<div><br/></div>`;
                }
            },
            insert(type){
                console.log(this.select, this.lastRange)
                // 获取光标对象的范围界定对象，一般就是textNode对象
                var node = this.lastRange.endContainer;

                // 获取光标位置
                var rangeStartOffset = this.lastRange.startOffset;

                let nextNode;
                if(!node.classList||!node.classList.contains('content')){
                    nextNode = node.nodeName == '#text'?node.parentNode.nextSibling:node.nextSibling;
                }
                // console.log(nextNode)

                switch (type) {
                    default: break;
                    case 'image':
                        const container = document.createElement('div');
                        const img = document.createElement('img');
                        this.imgSelect().then((path) => {
                            img.src = path;

                            container.appendChild(img);
                        });
                        img.onclick = (e) => {
                            this.imgEdit(e);
                        }

                        node = container;
                        if(null == nextNode) {
                            this.$refs.content.appendChild(node);
                        }else {
                            nextNode.before(node);
                        }
                        // this.lastRange.insertNode(container);

                        break;
                    case 'list':
                        const list = document.createElement('ul');
                        const li = document.createElement('li');
                        list.appendChild(li);

                        // this.lastRange.insertNode(list);
                        node = list;
                        if(null == nextNode) {
                            this.$refs.content.appendChild(node);
                        }else {
                            nextNode.before(node);
                        }
                        break;
                    case 'paragraph':
                        const p = document.createElement('p');
                        p.innerText = 'as';
                        // p.appendChild(document.createElement('p'))
                        // this.lastRange.insertNode(p);
                        node = p;
                        if(null == nextNode) {
                            this.$refs.content.appendChild(node);
                        }else {
                            nextNode.before(node);
                        }
                        break;
                    case 'center':
                        console.log(node.nodeName, this.select);

                        if(node.nodeName == '#text') {
                            node.parentElement.style.textAlign = 'center';
                        }else {
                            node.style.textAlign = 'center';
                        }

                        break;
                    case 'left':
                        if(node.nodeName == '#text') {
                            node.parentElement.style.textAlign = 'left';
                        }else {
                            node.style.textAlign = 'left';
                        }
                        break;
                    case 'right':
                        if(node.nodeName == '#text') {
                            node.parentElement.style.textAlign = 'right';
                        }else {
                            node.style.textAlign = 'right';
                        }
                        break;
                }

                // 光标移动到到原来的位置加上新内容的长度
                this.lastRange.setStartAfter(node)
                // 光标开始和光标结束重叠
                this.lastRange.collapse(true)
                // 清除选定对象的所有光标对象
                this.select.removeAllRanges()
                // 插入新的光标对象
                this.select.addRange(this.lastRange)
            },

            //获取光标位置
            getCursor(){
                var sel = getSelection();
                // console.log(sel)
                if(!sel){
                    return;
                }
                var node = sel.anchorNode;
                var isIn = false;
                while(node&&node.nodeType!=node.DOCUMENT_NODE){
                    var cls = node.classList;
                    // console.log(cls)
                    if(cls&&cls.contains("content")){
                        isIn = true;
                        break;
                    }
                    node=node.parentNode
                }
                // console.log('isIn', isIn)
                if(!isIn) return;
                // console.log("getCursor");
                this.select = sel;
                this.lastRange = sel.getRangeAt(0);
                // console.log(this.lastRange)
            },

            imgSelect(){
                const imgSelect = document.createElement('div');
                Object.assign(imgSelect.style, {
                    border: '1px solid rgb(238, 238, 238)',
                    borderRadius: '5px',
                    background: '#ffffff',
                    height: '36px',
                    lineHeight: '36px',
                    position: 'absolute',
                    padding: '0 10px',
                    zIndex: '101',
                })

                const imgInput = document.createElement('input');
                imgInput.type = 'file';
                imgSelect.appendChild(imgInput);

                const btnCancel = document.createElement('button');
                Object.assign(btnCancel.style, {
                    marginLeft: '10px',
                    background: '#158485',
                    color: '#ffffff'
                })
                btnCancel.innerText = '取消';
                btnCancel.onclick = (e) => {
                    this.$refs.editor.removeChild(imgSelect);
                    this.showModal = false;
                }
                imgSelect.appendChild(btnCancel)

                return new Promise((resolve) => {
                    //弹出图片选择框
                    this.showModal = true;

                    this.$refs.editor.appendChild(imgSelect);

                    imgInput.onchange = (e) => {
                        const files = e.target.files;
                        if(files.length > 0){
                            const file = files[0];
                            console.log(file);
                            if(this.remote) {
                                this.uploadFile(file).then((path) => resolve(path)).catch(() => {
                                    alert('上传失败！');
                                });
                            }else {
                                const fr = new FileReader();
                                fr.onload = (e1) => {
                                    const dataUrl = e1.target.result;
                                    console.log(this.remote)
                                    resolve(dataUrl);
                                }
                                fr.readAsDataURL(file);
                            }

                            this.showModal = false;
                            this.$refs.editor.removeChild(imgSelect);
                        }

                    };
                });
            },


            imgEdit(e){
                console.log(e)
                const img = e.target;

                const box = document.createElement('div');
                Object.assign(box.style, {
                    zIndex: 101,
                    textAlign: 'left',
                    // width: '100%',
                    height: '36px',
                    lineHeight: '36px',
                    border: '1px solid #eeeeee',
                    background: '#ffffff',
                    position: 'absolute',
                    top: '5px',
                    left: '15px',
                    padding: '0 10px'
                })
                box.innerHTML = `宽：<input type="number" name="width" style="width: 40px">高：<input type="number" name="height" style="width: 40px">`;

                const that = this;
                function close(){
                    img.style.border = '';
                    img.offsetParent.removeChild(box);
                    that.showModal = false;
                }

                const btnYes = document.createElement('button');
                Object.assign(btnYes.style, {
                    marginLeft: '10px',
                    background: '#158485',
                    color: '#ffffff'
                })
                btnYes.innerText = '确定';
                btnYes.onclick = (e) => {
                    // console.log(e);
                    const inputs = e.target.parentElement.getElementsByTagName('input');
                    if(inputs.width.value)
                    img.width = inputs.width.value;
                    if(inputs.height.value)
                    img.height = inputs.height.value;

                    close();
                }
                box.appendChild(btnYes);
                const btnCancel = document.createElement('button');
                Object.assign(btnCancel.style, {
                    marginLeft: '10px',
                    background: '#158485',
                    color: '#ffffff'
                })
                btnCancel.innerText = '取消';
                btnCancel.onclick = (e) => {
                    close();
                }
                box.appendChild(btnCancel);

                img.offsetParent.appendChild(box);
                img.style.border = '1px solid #6666ff';
                this.showModal = true;
            },

            uploadFile(file){
                return new Promise((resolve, reject) => {
                    if(this.url) {
                        let xhr = new XMLHttpRequest();
                        xhr.timeout = 3000;
                        xhr.ontimeout = function (event) {
                            alert("请求超时！");
                        }
                        let formData = new FormData();
                        formData.append('file', file);
                        xhr.open('POST', this.url);
                        xhr.send(formData);
                        xhr.onreadystatechange = () => {
                            console.log(xhr);
                            if (xhr.status == 200) {
                                if(xhr.readyState == 4) {
                                    const path = this.onsuccess(xhr.response);
                                    path && resolve(path);
                                }
                            }
                            else {
                                reject()
                            }
                        }
                    }
                })
            },

            getContent(){
                return this.$el.querySelector('.content').innerHTML;
            }
        }
    }
</script>

<style scoped>
    .h5-editor {
        width: 100%;
        border: 1px solid #d2dde6;
        border-radius: 5px;
        position: relative;
        display: flex;
        flex-direction: column;
    }

    .h5-editor .modal {
        position: absolute;
        width: 100%;
        height: 100%;
        background: #d2dde6;
        opacity: 0.2;
        z-index: 100;
    }

    .h5-editor .content {
        height: 100%;
        overflow: auto;
        text-align: left;
        padding: 2px 5px;
    }

    .h5-editor .content:empty::before {
        content: attr(placeholder);
        font-size: 14px;
        color: #CCC;
        line-height: 21px;
        padding-top: 10px;
        position: absolute;
        top: 2px;
    }

    .h5-editor .toolbar {
        display: flex;
        height: 30px;
        line-height: 30px;
        border-top: solid 1px #eeeeee;
        padding: 0 5px;
        background: #d2dde6;
    }

    .h5-editor .toolbar .item:not(:first-child) {
        margin-left: 18px;
    }
</style>