<template>
    <div :style="{height: '100%', backgroundImage: 'url('+bgImg+')'}">
        <login-panel @login="login"></login-panel>
    </div>
</template>

<script>
    import LoginPanel from '@/components/LoginPanel.vue'

    import {account} from "../store/types"

    export default {
        components: {LoginPanel},
        name: "Login",
        data(){
            return {
                bgImg: require("../assets/img/login_bg2.png")
            }
        },
        methods: {
            login(data){
                this.$store.commit(account.mutations.LOGIN, data);
            }
        }
    }
</script>

<style scoped>

</style>