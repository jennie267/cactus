<template>
    <div class="container-fluid mt--7">
        <div class="row">
            <div class="col">
                <div class="card shadow">
                    <div class="card-header bg-transparent row align-items-center">
                        <div >
                            <h3 class="mb-0" v-if="user.type == 'PARENT' " style="margin-right: 12%; margin-left: 12%; width: 120px;">
                                자녀목록 :
                            </h3>
                            <h3 class="mb-0" v-if="user.type == 'CHILD' " style="margin-right: 12%; margin-left: 12%; width: 120px;">
                                부모목록 :
                            </h3>
                        </div>
                        <div class=" text-left " style="margin-right: 1%;" >
                            <button   class="btn btn-neutral btn-icon " style="vertical-align: middle; background-color: #172b4d;" @click="checkedBtn('all')" >
                               <span class="btn-inner&#45;&#45;text" style="font-size: 18px; color: white; font-weight: 500;vertical-align: middle;">
                      전체
                      </span>

                            </button>
                        </div>
                        <div class=" text-left " v-for="child in children" :key="child.id" style="margin-right: 1%;" >
                            <button   class="btn btn-neutral btn-icon " style="vertical-align: middle;" @click="checkedBtn(child.userId)" >
                               <span href="#" class="avatar avatar-sm rounded-circle" style="vertical-align: middle;">
                          <img v-if="child.profileUrl != null" alt="" :src="child.profileUrl" style="width:90%;">
                      </span>
                                <span class="btn-inner&#45;&#45;text" style="font-size: 18px; color: #525f7f; font-weight: 500;vertical-align: middle;">
                      {{child.name}}
                      </span>

                            </button>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
<style>
    .navy{
        background-color: #2b2b58;
        color: white;
    }

    .white{
        background-color: white;
    }
</style>
<script>

    export default {
        name: 'children-table',
        data() {
            return {
                isActive: true,
                children:[]
            }
        },
        props: {
            user: Object
        },
        methods: {
            checkedBtn: function(userId){
                this.$parent.sendUserId(userId);
            },
            toggleClass: function(){
                // Check value
                if(this.isActive){
                    this.isActive = false;
                }else{
                    this.isActive = true;
                }

            },
            beforeSetting: function(revchildren){
                console.log('ChildrenTable  beforeSetting [Method] ',revchildren);
                this.children = revchildren;
            }
        }
    }

</script>
<style>
</style>
