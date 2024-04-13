<template>
    <b-container>
      <b-row>
        <b-col>
          <h2>Historial de Verificación de Anfitriones</h2>
        </b-col>
      </b-row>
      <b-row>
        <b-col>
          <b-table
            striped
            hover
            :items="solicitudes"
            :fields="fields"
          >
            <template #cell(actions)="data">
              <b-button size="sm" @click="aprobarSolicitud(data.item)">Aprobar</b-button>
              <b-button size="sm" variant="danger" @click="rechazarSolicitud(data.item)">Rechazar</b-button>
            </template>
            <template #cell(verDetalle)="data">
              <b-button size="sm" @click="verDetalle(data.item)">Ver Detalle</b-button>
            </template>
            <template #cell(documentos)="data">
              <a :href="data.item.urlDocumento" target="_blank">Descargar</a>
            </template>
          </b-table>
        </b-col>
      </b-row>
  
      <b-modal id="detalle-modal" title="Detalle de la Solicitud" header-bg-variant="dark" header-text-variant="light" body-bg-variant="light" footer-bg-variant="dark" footer-text-variant="light" size="lg">
        <div>
          <b>Nombre:</b> {{ detalleSolicitud.nombre }}
        </div>
        <div>
          <b>Correo:</b> {{ detalleSolicitud.correo }}
        </div>
        <div>
          <b>Fecha de Solicitud:</b> {{ detalleSolicitud.fechaSolicitud }}
        </div>
      </b-modal>
    </b-container>
  </template>
  
  <script>
  export default {
    name: 'HistorialVerificacionAnfitriones',
    data() {
      return {
        fields: [
          { key: 'nombre', label: 'Nombre' },
          { key: 'correo', label: 'Correo' },
          { key: 'fechaSolicitud', label: 'Fecha de Solicitud' },
          { key: 'actions', label: 'Acciones' },
          { key: 'verDetalle', label: 'Detalle' },
          { key: 'documentos', label: 'Documentos' }
        ],
        solicitudes: [
          { nombre: 'Juan Pérez', correo: 'juan@example.com', fechaSolicitud: '2024-03-29', urlDocumento: 'https://example.com/doc1.pdf' },
          { nombre: 'María López', correo: 'maria@example.com', fechaSolicitud: '2024-03-28', urlDocumento: 'https://example.com/doc2.pdf' }
        ],
        detalleSolicitud: {}
      };
    },
    methods: {
      aprobarSolicitud(solicitud) {
        // Lógica para aprobar la solicitud
        alert(`Solicitud de ${solicitud.nombre} aprobada`);
      },
      rechazarSolicitud(solicitud) {
        // Lógica para rechazar la solicitud
        alert(`Solicitud de ${solicitud.nombre} rechazada`);
      },
      verDetalle(solicitud) {
        this.detalleSolicitud = solicitud;
        this.$bvModal.show('detalle-modal');
      }
    }
  }
  </script>
  