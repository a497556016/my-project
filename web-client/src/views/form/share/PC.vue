<template>
    <div class="page">
        <div class="user-form">
            <div class="title">{{formData.title}}</div>
            <div class="desc">{{formData.desc}}</div>
            <div class="create-time">{{formData.createTime}}</div>
            <a-divider></a-divider>
            <a-form>
                <a-form-item v-for="(item, index) in formData.formItems" :label="(index+1)+'.'+item.question" :colon="false">
                    <component :is="item.component" v-model="item.meta"></component>
                </a-form-item>
                <a-form-item>
                    <a-button type="primary" @click="submit">提交</a-button>
                </a-form-item>
            </a-form>
        </div>

        <div class="img" v-if="!isMobile">
            <img :src="imgUrl" :style="{width: '100%', height: '100%'}">
        </div>
    </div>
</template>

<script>

    import {mapActions, mapState} from 'vuex'

    import {form as formTypes} from '../../../store/types'

    import MRadio from '@/components/formEditItems/items/Radio'
    import MCascade from '@/components/formEditItems/items/Cascade'
    import MSelect from '@/components/formEditItems/items/Select'
    import MInput from '@/components/formEditItems/items/Input'
    import MTextarea from '@/components/formEditItems/items/Textarea'
    import MRate from '@/components/formEditItems/items/Rate'
    import MCheckbox from '@/components/formEditItems/items/Checkbox'
    import MDate from '@/components/formEditItems/items/Date'
    import AFormItem from "ant-design-vue/es/form/FormItem";

    import QRCode from 'qrcode'

    export default {
        name: "PC",
        components: {AFormItem, MRadio, MCascade, MSelect, MInput, MTextarea, MRate, MCheckbox, MDate},
        props: ['formId'],
        data(){
            return {
                imgUrl: null
            }
        },
        computed: {
            ...mapState({
                formData: state => state.form.formData
            }),
            isMobile() {
                let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i);
                return flag;
            }
        },
        mounted() {
            this.loadFormData(this.formId);

            this.create2DImg();
        },
        methods: {
            ...mapActions({
                loadFormData: formTypes.FIND_BY_ID,
                submit: formTypes.SUBMIT_FORM
            }),
            create2DImg(){
                const href = window.location.href;
                console.log(href);
                QRCode.toDataURL(href)
                    .then(url => {
                        console.log(url)
                        this.imgUrl = url;
                    })
                    .catch(err => {
                        console.error(err)
                    })
            }
        }
    }
</script>

<style scoped lang="less">
    .page {
        width: 100%;
        height: 100%;
        background: #cccccc;

        .user-form {
            height: 100%;
            overflow-y: auto;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px 20px;
            background: white;

            .title {
                text-align: center;
                font-weight: bold;
                font-size: 26px;
                margin: 15px 0;
                color: #c72625;
            }

            .desc {
                font-size: 13px;
                color: #b3b3b3;
            }

            .create-time {
                font-size: 11px;
                color: #878787;
                text-align: right;
            }
        }

        .img {
            position: absolute;
            right: 10px;
            top: 10px;
            height: 100px;
            width: 100px;
            border: 1px solid #cccccc;
            box-shadow: 0px 0px 3px #ffbe83;
        }
    }

</style>