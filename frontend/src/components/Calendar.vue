<template>
  <div class="demo-app">
    <div class="demo-app-main">
      <FullCalendar class="demo-app-calendar" :options="calendarOptions" />
      <MoreModal :dialog="dialog" :focus="focus" @close="close" />
    </div>
  </div>
</template>

<script>
import FullCalendar from '@fullcalendar/vue';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import { createEventId } from './event-utils';

import MoreModal from './MoreModal';
import axios from 'axios';

export default {
  components: {
    FullCalendar, // make the <FullCalendar> tag available
    MoreModal,
  },

  data: function() {
    return {
      dialog: false,
      focus: null,
      calendarOptions: {
        events: [
          // {
          //   title: "올리브영 🥑",
          //   start: new Date("2021-01-01"),
          //   end: new Date("2021-01-10"),
          //   allDay:true,
          //   color: "#A2D42F",
          //   forceEventDuration:true
          // },
          // {
          //   title: '랄라블라 💗',
          //   start: new Date("2021-01-01"),
          //   end: new Date("2021-01-10"),
          //   allDay:true,
          //   color: "#012E40",
          //   // borderColor: 'black',
          // },
          // {
          //   title: "에뛰드 하우스 👑",
          //   start: new Date("2021-01-01"),
          //   end: new Date("2021-01-10"),
          //   allDay:true,
          //   color:"#F27EA9",
          // },
          // {
          //   title: "미샤 💋",
          //   start: new Date("2021-01-01"),
          //   end: new Date("2021-01-10"),
          //   allDay:true,
          //   color:"#D9043D",
          // },
          // {
          //   title: "아리따움 🎀",
          //   start: new Date("2021-01-01"),
          //   end: new Date("2021-01-10"),
          //   allDay:true,
          //   color:"#F2ACAC",
          // },
          // {
          //   title: "더 페이스샵 👩",
          //   start: new Date("2021-01-01"),
          //   end: new Date("2021-01-10"),
          //   allDay:true,
          //   color:"#9CBF4E",
          // },
          // {
          //   title: "토니모리 🐼",
          //   start: new Date("2021-01-01"),
          //   end: new Date("2021-01-10"),
          //   allDay:true,
          //   color:"#121212",
          // },
          // {
          //   title: "이니스프리 🌿",
          //   start: new Date("2021-01-01"),
          //   end: new Date("2021-01-10"),
          //   allDay:true,
          //   color:"#C0D99C",
          //   textColor:'black',
          // },
          {
            title: '올리브영 🥑',
            start: new Date('2021-01-01'),
            end: new Date('2021-01-10'),
            allDay: true,
            color: '#BDEDD1',
            forceEventDuration: true,
            textColor: '#50555C',
          },
          {
            title: '랄라블라 💗',
            start: '2021-01-05',
            end: new Date('2021-01-15'),
            allDay: true,
            color: '#FFCFDC',
            textColor: '#50555C',
          },
          {
            title: '에뛰드 하우스 👑',
            start: '2021-01-07',
            end: new Date('2021-01-17'),
            allDay: true,
            color: '#CFE4FF',
            textColor: '#50555C',
          },
          {
            title: '미샤 💋',
            start: '2021-01-10',
            end: new Date('2021-01-25'),
            allDay: true,
            color: '#FBEC8F',
            textColor: '#50555C',
          },
          {
            title: '아리따움 🎀',
            start: '2021-01-13',
            end: new Date('2021-01-23'),
            allDay: true,
            color: '#FFDABD',
            textColor: '#50555C',
          },
          {
            title: '더 페이스샵 👩',
            start: '2021-01-20',
            end: new Date('2021-01-30'),
            allDay: true,
            color: '#DFC6FF',
            textColor: '#50555C',
          },
          {
            title: '토니모리 🐼',
            start: '2021-01-25',
            end: new Date('2021-01-30, 15:00:00 GMT'),
            allDay: true,
            color: '#CCD1D1',
            textColor: '#50555C',
          },
          {
            title: '이니스프리 🌿',
            start: '2021-01-11',
            end: new Date('2021-01-30'),
            allDay: true,
            color: '#f7f8fb',
            textColor: '#50555C',
            id: '111',
            borderColor:'#CCD1D1',
          },
        ],
        plugins: [
          dayGridPlugin,
          timeGridPlugin,
          interactionPlugin, // needed for dateClick
        ],
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: '',
          // right: 'dayGridMonth,timeGridWeek,timeGridDay',
        },
        initialView: 'dayGridMonth',
        // editable: true,
        // selectable: true,
        // selectMirror: true,
        dayMaxEvents: true,
        weekends: true,
        select: this.handleDateSelect,
        locale: 'ko',
        eventClick: this.showMore,
        eventsSet: this.handleEvents,
        /* you can update a remote database when these fire:
        eventAdd:
        eventChange:
        eventRemove:
        */
      },
      currentEvents: [],
    };
  },
  created() {
    axios
      .get()
      .then((res) => {
        this.events = res.data;
      })
      .catch((err) => {
        console.log(err);
      });
  },
  methods: {
    showMore(clickInfo) {
      this.dialog = true;
      this.focus = clickInfo.event;
      console.log(clickInfo.event);
    },
    handleWeekendsToggle() {
      this.calendarOptions.weekends = !this.calendarOptions.weekends; // update a property
    },
    handleDateSelect(selectInfo) {
      let title = prompt('Please enter a new title for your event');
      let calendarApi = selectInfo.view.calendar;

      calendarApi.unselect(); // clear date selection

      if (title) {
        calendarApi.addEvent({
          id: createEventId(),
          title,
          start: selectInfo.startStr,
          end: selectInfo.endStr,
          allDay: selectInfo.allDay,
        });
      }
    },

    handleEventClick(clickInfo) {
      if (confirm(`Are you sure you want to delete the event '${clickInfo.event.title}'`)) {
        clickInfo.event.remove();
      }
    },

    handleEvents(events) {
      this.currentEvents = events;
    },
    close(dialog) {
      this.dialog = dialog;
    },
  },
};
</script>

<style lang="css">
h2 {
  margin: 0;
  font-size: 16px;
}

ul {
  margin: 0;
  padding: 0 0 0 1.5em;
}

li {
  margin: 1.5em 0;
  padding: 0;
}

b {
  /* used for event dates/times */
  margin-right: 3px;
}

.demo-app {
  display: flex;
  min-height: 100%;
  min-width: 50%;
  font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
  font-size: 14px;
}

.demo-app-sidebar {
  width: 300px;
  line-height: 1.5;
  background: #eaf9ff;
  border-right: 1px solid #d3e2e8;
}

.demo-app-sidebar-section {
  padding: 2em;
}

.demo-app-main {
  flex-grow: 1;
  padding: 3em;
}

.fc {
  /* the calendar root */
  max-width: 1100px;
  margin: 0 auto;
}
.fc .fc-col-header-cell-cushion {
  color: black;
}
.fc .fc-daygrid-day-number {
  color: black;
}
</style>
