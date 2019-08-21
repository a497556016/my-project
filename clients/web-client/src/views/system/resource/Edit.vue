<template>
    <div>
        <a-form>
            <a-form-item label="名称">
                <a-input v-model="role.name"></a-input>
            </a-form-item>
            <a-form-item label="编码">
                <a-input v-model="role.code"></a-input>
            </a-form-item>
        </a-form>
    </div>
</template>

<script>
    import {mapGetters} from 'vuex'
    import {role as types} from '@/store/types'
    export default {
        name: "Edit",
        data(){
            return {

            }
        },
        computed: {
            ...mapGetters({
                role: types.GET_EDIT_ROLE_DATA
            })
        }
    }
</script>

<style scoped>

</style>