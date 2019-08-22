<template>
    <div>
        <a-form>
            <a-form-item label="名称">
                <a-input v-model="permission.name"></a-input>
            </a-form-item>
            <a-form-item label="编码">
                <a-input v-model="permission.code"></a-input>
            </a-form-item>
        </a-form>
    </div>
</template>

<script>
    import {mapGetters} from 'vuex'
    import {permission} from '@/store/types'
    export default {
        name: "Edit",
        data(){
            return {

            }
        },
        computed: {
            ...mapGetters({
                permission: permission.GET_EDIT_PERMISSION_DATA
            })
        }
    }
</script>

<style scoped>

</style>