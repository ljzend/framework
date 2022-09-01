<template>
  <div class="dashboard-container">
    <userDashboard/>
  </div>
</template>

<script>
import userDashboard from './user'

export default {
  name: 'Dashboard',
  components: { userDashboard},
}
</script>
