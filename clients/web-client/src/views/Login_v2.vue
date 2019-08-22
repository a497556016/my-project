<template>
    <div class="login-page" :style="{backgroundImage: 'url('+bgImgs[bgIndex]+')'}">
        <div ref="floatPanel" class="float-panel" @mouseenter="actions.stop = true" @mouseleave="actions.stop = false" :style="{top: floatPanelStyle.top+'px', left: floatPanelStyle.left+'px'}">
            <div class="title">
                <marquee behavior=alternate direction=right align=middle>时代在召唤！</marquee>
            </div>
            <login-panel @login="login"></login-panel>
        </div>
    </div>
</template>

<script>
    import LoginPanel from "../components/LoginPanel";

    import {account} from "../store/types"

    const bgImgs = [
        require('../assets/img/login_bg3.jpg'),
        require('../assets/img/login_bg4.jpg')
    ]
    export default {
        name: "Login_v2",
        components: {LoginPanel},
        data(){
            return {
                floatPanelStyle: {
                    top: 100,
                    left: 100
                },
                actions: {
                    stop: false,
                    speed: 2
                },
                time: 20,
                interval: null,

                bgImgs,
                bgIndex: this.createBgIndex()
            }
        },
        mounted() {
            this.move();

            setInterval(() => {
                this.bgIndex = this.createBgIndex();
            }, 15000);
        },
        methods: {
            createBgIndex(){
                return Math.floor(Math.random()*(bgImgs.length));
            },
            move(){
                const maxLeft = window.innerWidth-this.$refs.floatPanel.clientWidth;
                const maxTop = window.innerHeight-this.$refs.floatPanel.clientHeight;

                this.floatPanelStyle.left = Math.floor(Math.random()*maxLeft);
                this.floatPanelStyle.top = Math.floor(Math.random()*maxTop);

                let step = this.createStep();
                this.interval = setInterval(() => {
                    // console.log(this.floatPanelStyle, maxLeft, maxTop, step)
                    if(this.actions.stop){
                        return;
                    }

                    if(this.floatPanelStyle.top <= 0) {
                        step.y *= -1;
                        this.floatPanelStyle.top = 0
                    }
                    if(this.floatPanelStyle.top >= maxTop) {
                        step.y *= -1;
                        this.floatPanelStyle.top = maxTop;
                    }
                    if(this.floatPanelStyle.left <= 0) {
                        step.x *= -1;
                        this.floatPanelStyle.left = 0;
                    }
                    if(this.floatPanelStyle.left >= maxLeft){
                        step.x *= -1;
                        this.floatPanelStyle.left = maxLeft;
                    }


                    this.floatPanelStyle.top += step.y;
                    this.floatPanelStyle.left += step.x;

                }, this.time);

            },
            createStep(){
                const x = Math.random()-Math.random();
                const y = Math.random()-Math.random();
                const l = (this.time * this.actions.speed)/20;
                return {x: x*l,y: y*l}
            },
            login(data){
                this.$store.commit(account.mutations.LOGIN, data);
            }
        }
    }
</script>

<style scoped lang="less">
    .login-page {
        width: 100%;
        height: 100%;
        position: relative;
        background: rgba(112, 247, 242, 0.53);

        .float-panel {
            position: absolute;

            max-width: 90%;
            width: 650px;
            max-height: 80%;
            height: 500px;
            border: 1px solid #84eede;
            border-radius: 5px;
            background: rgba(70, 212, 184, 0.6);

            .title {
                width: 100%;
                color: #ff362f;
                font-size: 32px;
                font-weight: bold;
                padding: 5px 15px;
                position: absolute;
            }
        }
    }


</style>