<template>
    <!-- Modals -->
    <div class="row">
        <div class="col-md-4">
            <modal :show.sync="modal">
                <h2 slot="header" class="modal-title" id="modal-title-default">일정 수정</h2>
                <form>
                    <div class="form-group row">
                        <label for="periodName3" class="col-sm-2 col-form-label">일정명</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="periodName3" v-model="period.name">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="periodRemark3" class="col-sm-2 col-form-label">내용</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="periodRemark3" v-model="period.remark">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-3 col-form-label">알람여부</label>
                        <div class="col-sm-9">
                            <div class="custom-control custom-checkbox mb-3">
                                <input class="custom-control-input" id="isAlarm3" type="checkbox" v-model="isAlarm">
                                <label class="custom-control-label" for="isAlarm3">
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-3 col-form-label">반복일정</label>
                        <div class="col-sm-9" valign="middle">
                            <div class="custom-control custom-checkbox mb-3">
                                <input class="custom-control-input" id="repSchedule3" type="checkbox" v-model="isCycle">
                                <label class="custom-control-label" for="repSchedule3">
                                </label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label">날짜</label>
                        <base-input class="col-sm-5" addon-left-icon="ni ni-calendar-grid-58">
                            <flat-pickr slot-scope="{focus, blur}"
                                        @on-open="focus"
                                        @on-close="blur"
                                        :config="{allowInput: true}"
                                        class="form-control datepicker"
                                        v-model="startDate">
                            </flat-pickr>
                        </base-input>
                        <base-input class="col-sm-5">
                            <flat-pickr slot-scope="{focus, blur}"
                                        @on-open="focus"
                                        @on-close="blur"
                                        :config=timeConfig
                                        class="form-control datepicker"
                                        v-model="startTime">
                            </flat-pickr>
                        </base-input>
                    </div>
                    <div class="form-group" v-if="isCycle">
                        <div class="form-group row">
                            <div class="col-sm-2"><h1>~</h1></div>
                            <base-input class="col-sm-5" addon-left-icon="ni ni-calendar-grid-58">
                                <flat-pickr slot-scope="{focus, blur}"
                                            @on-open="focus"
                                            @on-close="blur"
                                            locale="ko"
                                            :config="{allowInput: true}"
                                            class="form-control datepicker"
                                            v-model="endDate">
                                </flat-pickr>
                            </base-input>
                            <base-input class="col-sm-5">
                                <flat-pickr slot-scope="{focus, blur}"
                                            @on-open="focus"
                                            @on-close="blur"
                                            :config=timeConfig
                                            class="form-control datepicker"
                                            v-model="endTime">
                                </flat-pickr>
                            </base-input>
                        </div>
                        <div class="form-group row">
                            <label for="periodFreq3" class="col-sm-2 col-form-label">주기</label>
                            <div class="col-sm-4">
                                <input type="text" class="form-control" id="periodFreq3" v-model="period.freq">
                            </div>
                            <div class="col-sm-6">
                                <multiselect v-model="cycle" track-by="name" label="name" placeholder="선택" :options="options" :searchable="false" :allow-empty="true">
                                    <template slot="singleLabel" slot-scope="{ option }"><strong>{{ option.name }}</strong></template>
                                </multiselect>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="periodPlace3" class="col-sm-2 col-form-label">장소</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="periodPlace3" v-model="period.place">
                        </div>
                    </div>
                    <div class="form-group row">
                        <label for="periodManager3" class="col-sm-2 col-form-label">담당자</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="periodManager3" v-model="period.manager">
                        </div>
                    </div>
                </form>
                <template slot="footer">
                    <base-button type="primary" class="active btn-primary" @click="confirm">수정</base-button>
                    <base-button type="secondary" class="active ml-3" @click="modal = false">취소
                    </base-button>
                </template>
            </modal>
        </div>
    </div>
</template>
<script>
    import Modal from "@/components/Modal.vue";
    import flatPickr from 'vue-flatpickr-component';
    import Multiselect from 'vue-multiselect'
    import 'flatpickr/dist/flatpickr.css';
    import 'vue-multiselect/dist/vue-multiselect.min.css'
    var moment = require('moment');
    moment().format();

    export default {
        components: {
            Modal
            , flatPickr
            , Multiselect
        },
        watch: {
            modal: function() {
                if(this.period.isAlarm==='Y') this.isAlarm = true;
                else this.isAlarm = false;
                if(this.period.cycle !== null && this.period.freq !== null) this.isCycle = true;
                else this.isCycle = false;
                if(this.period.cycle !== null) {
                    let tmpCycle = {name:"",value:""};
                    this.options.forEach(option => {
                       if(option.value === this.period.cycle) tmpCycle.name = option.name;
                    });
                    tmpCycle.value = this.period.cycle;

                    this.cycle = tmpCycle;
                }
                if(this.period.startTime !== null) {
                    this.startDate = moment(this.period.startTime, "YYYY-MM-DDTHH:mm:ssZ").format('YYYY-MM-DD');
                    this.startTime = moment(this.period.startTime, "YYYY-MM-DDTHH:mm:ssZ").format('HH:mm');
                }
                if(this.period.endTime !== null) {
                    this.endDate = moment(this.period.endTime, "YYYY-MM-DDTHH:mm:ssZ").format('YYYY-MM-DD');
                    this.endTime = moment(this.period.endTime, "YYYY-MM-DDTHH:mm:ssZ").format('HH:mm');
                }
                this.period.lastTime = null;
            },
            startDate: function() {
                this.period.startTime = this.startDate===""?"":this.startDate+'T'+this.startTime;
            },
            startTime: function() {
                this.period.startTime = this.startDate===""?"":this.startDate+'T'+this.startTime;
            },
            endDate: function() {
                this.period.endTime = this.endDate===""?"":this.endDate+'T'+this.endTime;
            },
            endTime: function() {
                this.period.endTime = this.endDate===""?"":this.endDate+'T'+this.endTime;
            },
            isAlarm: function() {
                this.period.isAlarm = this.isAlarm===true?"Y":"N";
            },
            isCycle: function() {
                if(this.isCycle===false) {
                    this.endDate = "";
                    this.endTime = "";
                    this.cycle = null;
                    this.period.freq = "";
                }
            },
            cycle: function() {
                this.period.cycle = this.cycle===null?"":this.cycle.value;
            },
            selectedParent: function() {
                this.period.userId = this.selectedParent===null?"":this.selectedParent.userId;
            }
        },
        data() {
            return {
                modal: false,
                periodId: null,
                user: this.$store.state.user,
                isAlarm: false,
                isCycle: false,
                timeConfig:{
                    allowInput: true,
                    enableTime: true,
                    noCalendar: true,
                    dateFormat: "H:i",
                    defaultDate: "00:00"
                },
                config: {
                    wrap: true,
                    altFormat: 'M j, Y',
                    altInput: true,
                    dateFormat: 'Y-m-d',
                },
                value: null,
                options: [
                    { name: '년', value:'Y'},
                    { name: '월', value:'M'},
                    { name: '주', value:'W'},
                    { name: '일', value:'D'},
                    { name: '시간', value:'H'},
                    { name: '분', value:'MM'},
                ],
                startDate: "",
                startTime: "00:00",
                endDate: "",
                endTime: "00:00",
                cycle: {},
                period: {}
            };
        },
        methods:{
            confirm() {
                this.$http.post(`/period/update/${this.period.periodId}`, this.period,
                    {
                        headers: {
                            Authorization: `Bearer ${this.user.token}`,
                            'Content-Type': 'application/json;charset=UTF-8'
                        }
                    }
                    )
                    .then(res => {
                        if(res.status===200) {
                            this.$swal({
                                type: 'success',
                                title: '수정되었습니다.'
                            });
                            this.modal = false;

                            this.$http.post(`/period/schedule/period/delete/${this.period.periodId}`, this.period,
                                {
                                    headers: {
                                        Authorization: `Bearer ${this.user.token}`
                                    }
                                }
                            ).then(res => {
                                if(res.status===200) {
                                    this.$http.post(`/period/schedule/period/${this.period.periodId}`, this.period,
                                        {
                                            headers: {
                                                Authorization: `Bearer ${this.user.token}`,
                                                'Content-Type': 'application/json;charset=UTF-8'
                                            }
                                        }
                                    ).then(res => {
                                        if(res.status===200) {
                                            this.$store.commit('setUpdatedPeriodId', this.period.periodId);
                                        }
                                    });
                                }
                            });
                        }
                    });
            }
        }
    };

</script>
<style>
</style>
