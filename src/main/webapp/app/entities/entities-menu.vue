<template>
  <div>
    <b-dropdown-item to="/country">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.country')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/province">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.province')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/town-city">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.townCity')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/district">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.district')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/common-locality">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.commonLocality')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/tenant">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.tenant')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/type-of-organization">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.typeOfOrganization')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/organization">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.organization')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/business-unit">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.businessUnit')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/organization-domain">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.organizationDomain')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/organization-attribute">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.organizationAttribute')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/type-of-person">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.typeOfPerson')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/person">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.person')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/organization-role">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.organizationRole')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/organization-membership">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.organizationMembership')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/organization-member-role">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.organizationMemberRole')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/asset-type">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.assetType')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/raw-asset-proc-tmp">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.rawAssetProcTmp')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/asset">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.asset')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/asset-metadata">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.assetMetadata')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/asset-collection">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.assetCollection')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/invoice">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.invoice')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/payment-gateway">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.paymentGateway')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/payment">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.payment')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/warehouse">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.warehouse')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/supplier">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.supplier')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/brand">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.brand')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/product">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.product')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/inventory-transaction">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.inventoryTransaction')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/stock-level">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.stockLevel')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/customer">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.customer')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/customer-type">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.customerType')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/category">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.category')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/article">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.article')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/order">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.order')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/order-item">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.orderItem')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/type-of-artmedia">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.typeOfArtmedia')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/type-of-artist">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.typeOfArtist')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/artistic-genre">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.artisticGenre')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/artist">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.artist')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/artwork">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.artwork')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/artwork-cast">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.artworkCast')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/type-of-event">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.typeOfEvent')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/type-of-venue">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.typeOfVenue')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/type-of-activity">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.typeOfActivity')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/venue">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.venue')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/activity">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.activity')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/event">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.event')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/program">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.program')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/event-program">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.eventProgram')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/scheduled-activity">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.scheduledActivity')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/event-attendee">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.eventAttendee')"></span>
    </b-dropdown-item>
    <b-dropdown-item to="/ticket-purchased">
      <font-awesome-icon icon="asterisk" />
      <span v-text="t$('global.menu.entities.ticketPurchased')"></span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
