<template>
    <div>
        <div class="color-input">
            <a-input v-model="color"></a-input>
            <div class="color" :style="{background: value}" @click="showPicker"></div>
        </div>

        <sketch v-if="showSketch" style="z-index: 101;position: absolute;" :value="value" @input="updateColor"></sketch>
        <div v-if="showSketch" style="position: fixed;top: 0; left: 0;width: 100%;height: 100%;z-index: 100;background: #cccccc;opacity: 0.0;" @click="showSketch = false"></div>
    </div>
</template>

<script>
    import { Sketch } from 'vue-color'
    export default {
        name: "ColorPicker",
        components: {Sketch},
        props: {
            value: String
        },
        model: {
            prop: 'value',
            event: 'change'
        },
        data(){
            return {
                showSketch: false,
                color: this.value
            }
        },
        watch: {
            color(c) {
                this.$emit('change', c);
            }
        },
        methods: {
            showPicker(){
                this.showSketch = true;
            },
            updateColor(color){
                console.log(color);
                this.color = color.hex;
            }
        }
    }
</script>

<style scoped lang="less">
    .color-input {
        display: flex;

        .color {
            width: 50px;
            border-radius: 0 5px 5px 0;
            cursor: pointer;
        }
    }
</style>