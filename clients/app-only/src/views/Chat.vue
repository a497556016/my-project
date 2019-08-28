<template>
    <div class="chat-page">
        <div class="actions-sheet" v-if="actionsVisible">
            <div class="modal" @click="actionsVisible = false"></div>
            <div class="item">
                <i class="fa fa-circle-o"></i>&nbsp;&nbsp;切换对象
            </div>
            <div class="item">
                <i class="fa fa-circle-o"></i>&nbsp;&nbsp;退出登录
            </div>
        </div>

        <div class="title">
            <div>
                <i class="fa fa-heart-o"></i>
            </div>
            {{lineUserInfo.nickname}}
            <div class="actions" @click="showActions">
                <i class="fa fa-plus"></i>
            </div>
        </div>

        <div ref="content" class="content">
            <div class="split-tips"><span>打个招呼吧</span></div>

            <msg-box v-for="record in hisChatRecords" :record="record" :type="msgClass(record)"></msg-box>

            <div class="split-tips" v-if="hisChatRecords.length > 0"><span>以上是历史消息</span></div>

            <!--<msg-box :record="{from: {avatar: require('../assets/logo.png')}, msg: '你好，我是某某某！'}" type="left"></msg-box>-->
            <!--<msg-box :record="{from: {avatar: require('../assets/logo.png')}, msg: '滚！'}" type="right"></msg-box>-->
            <!--<msg-box :record="{from: {avatar: require('../assets/logo.png')}, msg: '你dfjsukj健康的计算机开发你受苦尽可能上岛咖啡跟你说的功能开始就开始更健康送给你！'}" type="left"></msg-box>-->

            <msg-box v-for="record in chatRecords" :record="record" :type="msgClass(record)"></msg-box>
        </div>

        <div class="footer">
            <div class="icon"><i class="fa fa-volume-up"></i></div>
            <div class="input">
                <textarea rows="1" v-model="inputWords"></textarea>
            </div>
            <div class="icon"><i class="fa fa-smile-o"></i></div>
            <div class="icon" v-if="!inputWords"><i class="fa fa-plus-circle"></i></div>
            <button class="send-btn" v-if="inputWords" @click="sendMsg">发送</button>
        </div>
    </div>
</template>

<script>
    import ImServer from '../util/io'
    import {createNamespacedHelpers} from 'vuex'
    const userStore = createNamespacedHelpers("user");
    const chatStore = createNamespacedHelpers("chat");
    import {user as userTypes, chat as chatTypes} from '../store/types'
    import MsgBox from "../components/chat/MsgBox";
    export default {
        name: "Home",
        components: {MsgBox},
        data(){
            return {
                inputWords: '',
                actionsVisible: false
            }
        },
        computed: {
            ...chatStore.mapState({
                hisChatRecords: state => state.hisChatRecords,
                chatRecords: state => state.chatRecords
            }),
            ...userStore.mapGetters({
                lineUserInfo: userTypes.GET_LINE_USER_INFO,
                loginUserInfo: userTypes.GET_LOGIN_USER
            })
        },
        watch: {
            chatRecords(){
                const el = this.$refs.content;
                console.log(el.scrollTop, el.scrollHeight)
                setTimeout(() => {
                    el.scrollTo({
                        top: el.scrollHeight+500
                    })
                }, 500)

            }
        },
        mounted(){
            this.loadHisChatRecord();
            ImServer.init();
        },
        methods: {
            ...chatStore.mapActions({
                loadHisChatRecord: chatTypes.LOAD_HIS_CHAT_RECORDS
            }),
            ...chatStore.mapMutations({
                addChatRecord: chatTypes.ADD_CHAT_RECORD
            }),
            msgClass(record){
                return (record.from.username == this.loginUserInfo.username && record.to.username != this.loginUserInfo.username)?'right':'left';
            },
            sendMsg(){
                this.iMServer.send({
                    type: 'user',
                    msg: this.inputWords,
                    from: {
                        id: this.loginUserInfo.id,
                        avatar: require('../assets/logo.png'),//this.loginUserInfo.avatar,
                        username: this.loginUserInfo.username,
                        nickname: this.loginUserInfo.nickname
                    },
                    to: {
                        id: this.lineUserInfo.id,
                        avatar: require('../assets/logo.png'),//this.lineUserInfo.avatar,
                        username: this.lineUserInfo.username,
                        nickname: this.lineUserInfo.nickname
                    }
                });
                this.inputWords = '';
            },
            showActions(){
                this.actionsVisible = true;
            }
        }
    }
</script>

<style scoped lang="less">

    /*.avatar(){
        > * {
            width: 36px;
            height: 36px;
        }
    }*/

    .actions-sheet{
        position: absolute;
        right: 10px;
        top: 10px;
        display: inline-block;
        z-index: 100;
        padding: 5px 10px;
        background: white;
        .item {
            font-size: 13px;
            padding: 10px 0;
            &:not(:last-child){
                border-bottom: 1px solid #d2dde6;
            }
        }
        .modal {
            z-index: 99;
            position: fixed;
            left: 0;
            right: 0;
            top: 0;
            bottom: 0;
            background: #d2dde6;
            opacity: 0.3;
        }
    }

    .chat-page {
        background: #f8f8f8;
        height: 100%;
        .title{
            height: 2.5rem;
            line-height: 2.5rem;
            color: #000;
            text-align: center;
            border-bottom: 0.01rem solid #e3e3e3;

            padding: 0 15px;

            position: fixed;
            left: 0;
            right: 0;
            display: flex;
            justify-content: space-between;

            .actions {
                font-size: 16px;
            }
        }

        .content {
            position: absolute;
            top: 2.5rem;
            bottom: 3.6rem;
            overflow-y: scroll;
            /*padding: 10px;*/
            width: 100%;
            .split-tips {
                margin: 10px 0;
                text-align: center;
                span{
                    font-size: 0.6rem;
                    padding: 3px 5px;
                    border-radius: 5px;
                    color: white;
                    background: #d2dde6;
                }
            }

            /*.msg {
                .avatar();

                display: inline-block;
                width: 100%;
                line-height: 36px;
                margin: 15px 0;
                .text {
                    !*width: 100%;*!
                    border: 0px solid #d2dde6;
                    border-radius: 5px;

                    color: #e7f3fd;
                    height: 100%;
                    width: fit-content;
                    padding: 0 5px;
                    max-width: 70%;
                    display: table;
                    position: relative;

                    white-space:pre-wrap;
                }

            }
            .left {
                .text {
                    float: left;
                    margin-left: 12px;
                    background: #fff;
                    color: #000;
                }
                .text:before{
                    position: absolute;
                    content: "";
                    width: 0;
                    height: 0;
                    right: 100%;
                    top: 8px;
                    border-top: 6px solid transparent;
                    border-right: 10px solid #fff;
                    border-bottom: 6px solid transparent;
                }
                .avatar {
                    float: left;
                }
            }

            .right {
                .text {
                    float: right;
                    margin-right: 12px;
                    background: #3fcb6e;
                }
                .text:after{
                    position: absolute;
                    content: "";
                    width: 0;
                    height: 0;
                    left: 100%;
                    top: 8px;
                    border-top: 6px solid transparent;
                    border-left: 10px solid #3fcb6e;
                    border-bottom: 6px solid transparent;
                }
                .avatar {
                    float: right;
                }
            }*/
        }

        .footer {
            position: fixed;
            bottom: 0;
            left: 0;
            width: 100%;
            height: 3.6rem;
            line-height: 3.6rem;
            border-top: 0.01rem solid #e3e3e3;
            display: flex;
            background: #f8f8f8;

            .icon {
                width: 36px;
                margin: 8px 5px;
                text-align: center;
                font-size: 1.6rem;
                i{
                    display: block;
                    margin-top: 0.6rem;
                }
            }

            .input {
                padding: 0 10px;
                flex: 1;
                textarea {
                    font-size: 1.5rem;
                    color: #333;
                    width: 100%;
                    background: #fff;
                    border: 1px solid #89b9eb;
                    border-radius: 5px;
                    height: 2.0rem;
                    margin-top: 10px;
                }
            }

            .send-btn {
                background: #3fcb6e;
                border: 0;
                border-radius: 5px;
                color: #fff;
                width: 46px;
                margin: 8px 5px;
            }
        }
    }
</style>