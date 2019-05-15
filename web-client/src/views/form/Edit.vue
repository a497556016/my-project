<template>
    <edit-form :save-form="updateForm" v-model="formData"></edit-form>
</template>

<script>
    import EditForm from '@/components/formEditItems/EditForm'

    import {form as formTypes} from '@/store/types'

    import {mapActions} from 'vuex'

    export default {
        name: "Edit",
        components: {EditForm},
        props: {
            data: Object
        },
        data(){
            return {
                formData: this.data
            }
        },
        methods: {
            ...mapActions({
                updateForm: formTypes.UPDATE_FORM
            })
        }
    }
</script>

<style scoped>

</style>