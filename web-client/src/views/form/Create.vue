<template>
    <edit-form :save-form="saveForm" v-model="formData"></edit-form>
</template>

<script>
    import EditForm from '@/components/formEditItems/EditForm'

    import {form as formTypes} from '@/store/types'

    import {mapActions} from 'vuex'

    export default {
        name: "Create",
        components: {
            EditForm
        },
        data(){
            return {
                formData: {
                    title: '',
                    desc: '感谢您能抽时间参与本次问卷，您的意见和建议就是我们前行的动力！',
                    meta: {
                        titleColor: '#cf5724'
                    },
                    formItems: []
                }
            }
        },
        methods: {
            ...mapActions({
                saveForm: formTypes.SAVE_FORM
            })
        }
    }
</script>

<style scoped lang="less">

</style>