<template>
    <div class="posts-create">
        <my-nav-bar title="发表内容" left-arrow fixed @click-left="$router.back()"></my-nav-bar>

        <div class="form">
            <div class="title van-hairline--bottom">
                <van-field placeholder="请输入标题" size="large" v-model="title"></van-field>
            </div>
            <div class="editor-content van-hairline--bottom">
                <h5-editor ref="editor" :height="300" :remote="true" :url="'http://127.0.0.1:7002/file-server/upload'" :onsuccess="onSuccess"></h5-editor>
                <!--<editor :height="editorHeight" :content="content" :show-module-name="false" ref="editor" @change="updateData"></editor>-->
            </div>
        </div>

        <div class="tools">
            <!--<div class="icons">
                <van-icon name="photo"></van-icon>
            </div>-->
            <van-button type="primary" @click="submit">提交</van-button>
        </div>
    </div>
</template>

<script>
    import MyNavBar from "../../../components/navbar/index";
    import {Field, Button, Icon} from 'vant'
    /*import VueHtml5Editor from 'vue-html5-editor'
    const Editor = new VueHtml5Editor({
        showModuleName: false,
        image: {
            sizeLimit: 10 * 1024 * 1024,
            compress: true,
            width: 500,
            height: 500,
            quality: 80
        },
        icons: {
            text: "fa fa-pencil",
            color: "fa fa-paint-brush",
            font: "fa fa-font",
            align: "fa fa-align-justify",
            list: "fa fa-list",
            link: "fa fa-chain",
            unlink: "fa fa-chain-broken",
            tabulation: "fa fa-table",
            image: "fa fa-file-image-o",
            hr: "fa fa-minus",
            eraser: "fa fa-eraser",
            undo: "fa-undo fa",
            "full-screen": "fa fa-arrows-alt",
            info: "fa fa-info",
        },
        language: "zh-cn",
        hiddenModules: [
            "info",
            "list",
            "eraser",
            "undo",
            "full-screen",
        ],
        visibleModules: [
            "text",
            "color",
            "font",
            "align",
            "link",
            "unlink",
            "tabulation",
            "image",
            "hr",

        ],
    })*/
    export default {
        name: "PostsCreate",
        components: {
            // Editor,
            MyNavBar,
            [Field.name]: Field,
            [Button.name]: Button,
            [Icon.name]: Icon
        },
        data(){
            return {
                title: '',
                content: ''
            }
        },
        mounted() {
            // const editor = this.$refs.editor;
            // const toolbar = editor.$refs.toolbar.childNodes[0];
            // console.log(toolbar)
            // toolbar.style.position = 'fixed';
            // toolbar.style.bottom = '44px';
            // toolbar.style.width = '100%';
            // toolbar.style.backgroundColor = 'white';
            // toolbar.style.borderTop = '1px solid #eeeeee';
        },
        computed: {
            // editorHeight(){
            //     return (window.innerHeight - 110);
            // }
        },
        methods: {
            submit(){
                console.log(this.$refs.editor.getContent());
                if(!this.title) {
                    this.$toast.fail('请输入标题！')
                    return;
                }

            },
            // updateData(data){
            //     this.content = data;
            // },

            onSuccess(response){
                const res = JSON.parse(response);
                if(res.code == 1) {
                    return 'http://127.0.0.1:7002/file-server/download?path='+encodeURI(res.data.path);
                }else {
                    return null;
                }
            }
        }
    }
</script>

<style scoped lang="less">
    .posts-create {
        padding: 50px 0 50px 0;
        .form {
            .title {
                height: 50px;
                .van-cell {
                    /*font-weight: bold;*/
                    font-size: 18px;
                }
            }
            .editor-content {
                .vue-html5-editor{
                    .toolbar {
                        ul{
                            position: fixed;
                            bottom: 50px;
                            width: 100%;
                        }
                    }
                }

            }
        }

        .tools {
            position: fixed;
            bottom: 0;
            height: 44px;
            line-height: 55px;
            width: 100%;
            background: #eeeeee;

            .icons {
                float: left;
                font-size: 28px;
                color: #aaa;
                .van-icon {
                    margin-left: 10px;
                }
            }
            .van-button{
                float: right;
            }
        }
    }
</style>