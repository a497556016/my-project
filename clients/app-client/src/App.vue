<template>
    <div id="app">
        <router-view></router-view>
    </div>
</template>

<script>

    export default {
        name: 'app',
        mounted() {
            /*document.addEventListener('deviceready', () => {
                this.$toast('deviceready')
                document.addEventListener('backbutton', () => {
                    this.$toast('backbutton')
                    if (this.isHomePage()) {
                        navigator.app.exitApp(); //退出app
                    } else {
                        this.$router.back();
                    }
                })
            })*/
        },
        methods: {
            /*isHomePage() {
                const path = this.$router.path.toString();
                this.$toast(path);
                if (path == '/main/home') {
                    return true;
                }
                return false;
            }*/
        }
    }
</script>

<style>
    #app {
        font-family: 'Avenir', Helvetica, Arial, sans-serif;
        -webkit-font-smoothing: antialiased;
        -moz-osx-font-smoothing: grayscale;
    }
</style>
