package ua.farmapriv.presentacion.servicios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import ua.farmapriv.presentacion.utilerias.ConstantesParametros;

@Stateless
public class ServicioNotificacion {

	@Resource(name = ConstantesParametros.JNDI_MAIL)
	private Session mailSession;

	private void envioMensajeMail(String mailEmisor, String mailReceptor,
			String mailCopia, String asunto, String cuerpo)
			throws javax.mail.SendFailedException,
			javax.mail.MessagingException, Exception {

		try {
			MimeMultipart multiParte = obtenerMensajeMixto(cuerpo, null, null);
			MimeMessage mimeMensaje = new MimeMessage(mailSession);
			mimeMensaje.setFrom(new InternetAddress(mailEmisor));

			// Siempre
			mimeMensaje.addRecipients(Message.RecipientType.TO,
					destinatariosMail(Arrays.asList(mailReceptor.split(";"))));
			// siempre
			if (mailCopia != null && !("".equals(mailCopia))) {
				mimeMensaje.addRecipients(Message.RecipientType.CC,
						destinatariosMail(Arrays.asList(mailCopia.split(";"))));
			}

			mimeMensaje.setSubject(asunto);
			mimeMensaje.setContent(multiParte);
			// mimeMensaje.addHeader("Disposition-Notification-To",mailEmisor);
			Transport.send(mimeMensaje);
		} catch (javax.mail.SendFailedException mx) {

			mx.printStackTrace(System.err);

			StringBuilder errorStack = new StringBuilder();
			StringBuilder errorSB = null;

			if (mx.getInvalidAddresses() != null) {
				errorSB = new StringBuilder();
				for (Address email : mx.getInvalidAddresses()) {
					errorSB.append(email.toString());
					errorSB.append(", ");
				}

				errorStack.append("Direccion no valida Encontrada: "
						+ errorSB.toString());

				// System.out.println("Invalid Address Found: "+ errorSB);
			}

			if (mx.getValidSentAddresses() != null) {
				errorSB = new StringBuilder();
				for (Address email : mx.getValidSentAddresses()) {
					errorSB.append(email.toString());
					errorSB.append(", ");
				}
				errorStack
						.append("Correo electrónico enviado a la dirección válida:  "
								+ errorSB.toString());
				// System.out.println("Email sent to valid address: "+ errorSB);
			}

			if (mx.getValidUnsentAddresses() != null) {
				errorSB = new StringBuilder();
				for (Address email : mx.getValidUnsentAddresses()) {
					errorSB.append(email.toString());
					errorSB.append(", ");
				}
				errorStack.append("Email enviado a dirección no válida: "
						+ errorSB);
			}
			throw new javax.mail.SendFailedException(errorStack.toString());

		} catch (javax.mail.MessagingException mx) {
			throw mx;
		} catch (Exception ex) {
			throw ex;
		}
	}

	private InternetAddress[] destinatariosMail(List<String> destinatarios)
			throws Exception {
		InternetAddress[] direcciones = new InternetAddress[destinatarios
				.size()];
		for (int i = 0; i < destinatarios.size(); i++) {
			direcciones[i] = new InternetAddress(destinatarios.get(i));
		}
		return direcciones;
	}

	private MimeMultipart obtenerMensajeMixto(String mensaje,
			List<String> archivos, String archXml) throws Exception {
		MimeMultipart multiParte = new MimeMultipart();
		BodyPart texto = new MimeBodyPart();
		texto.setText(mensaje);
		multiParte.addBodyPart(texto);
		if (archivos != null) {
			for (String rutaArchivoAjunto : archivos) {
				BodyPart adjunto = new MimeBodyPart();
				FileDataSource arch = new FileDataSource(rutaArchivoAjunto);
				adjunto.setDataHandler(new DataHandler(arch));
				adjunto.setFileName(arch.getName());
				multiParte.addBodyPart(adjunto);
			}
			BodyPart adjuntoXml = new MimeBodyPart();
			adjuntoXml.setDataHandler(new DataHandler(archXml, "text/xml"));
			adjuntoXml.setFileName("autorizacion.xml");
			multiParte.addBodyPart(adjuntoXml);
		}
		return multiParte;
	}

	public void notificacionBienvenida(String mailEmisor, String mailReceptor,
			String mailCopia, String asunto, String cuerpo)
			throws SendFailedException, MessagingException, Exception {
		envioMensajeMail(mailEmisor, mailReceptor, mailCopia, asunto, cuerpo);

	}

	@Asynchronous
	public void notificacionMail(String mailEmisor, String mailReceptor,
			String mailCopia, String asunto, String cuerpo)
			throws SendFailedException, MessagingException, Exception {
		envioMensajeMail(mailEmisor, mailReceptor, mailCopia, asunto, cuerpo);

	}

	public void envioMensajeMailComprobantes(String mailEmisor,
			String mailReceptor, String asunto, String cuerpo, String compPdf,
			String compXml) throws javax.mail.SendFailedException,
			javax.mail.MessagingException, Exception {

		List<String> listaComprobante = new ArrayList<String>();
		listaComprobante.add(compPdf);

		try {
			MimeMultipart multiParte = obtenerMensajeMixto(cuerpo,
					listaComprobante, compXml);
			MimeMessage mimeMensaje = new MimeMessage(mailSession);
			mimeMensaje.setFrom(new InternetAddress(mailEmisor));

			mimeMensaje.addRecipients(Message.RecipientType.TO,
					destinatariosMail(Arrays.asList(mailReceptor.split(";"))));

			mimeMensaje.setSubject(asunto);
			mimeMensaje.setContent(multiParte);
			Transport.send(mimeMensaje);
		} catch (javax.mail.SendFailedException mx) {

			StringBuilder errorStack = new StringBuilder();
			StringBuilder errorSB = null;

			if (mx.getInvalidAddresses() != null) {
				errorSB = new StringBuilder();
				for (Address email : mx.getInvalidAddresses()) {
					errorSB.append(email.toString());
					errorSB.append(", ");
				}

				errorStack.append("Direccin no valida Encontrada: "
						+ errorSB.toString());
			}

			if (mx.getValidSentAddresses() != null) {
				errorSB = new StringBuilder();
				for (Address email : mx.getValidSentAddresses()) {
					errorSB.append(email.toString());
					errorSB.append(", ");
				}
				errorStack
						.append("Correo electrónico enviado a la dirección válida:  "
								+ errorSB.toString());
			}

			if (mx.getValidUnsentAddresses() != null) {
				errorSB = new StringBuilder();
				for (Address email : mx.getValidUnsentAddresses()) {
					errorSB.append(email.toString());
					errorSB.append(", ");
				}
				errorStack
						.append("Email not sent to valid address: " + errorSB);
			}

			throw new javax.mail.SendFailedException(errorStack.toString());

		} catch (javax.mail.MessagingException mx) {
			throw mx;
		} catch (Exception ex) {
			throw ex;
		}
	}

	public void notificacionRideConAdjuntos(String mailEmisor,
			String mailReceptor, String mailCopia, String asunto,
			String cuerpo, String xml, String claveAcceso , byte[] pdfFile )
			throws Throwable {
		System.out.println("metodo de notificacion");
		List<BodyPart> adjuntos = new ArrayList<BodyPart>();
		adjuntos.add(obtenerAdjuntoTexto(xml, "text/xml", claveAcceso + ".xml"));
		adjuntos.add(obtenerCuerpo(cuerpo));
		 adjuntos.add(obtenerAdjuntoMemoria(pdfFile, "application/pdf",
		 claveAcceso + ".pdf"));
		envioMensajeMailAdj(mailEmisor, mailReceptor, mailCopia, asunto,
				cuerpo, adjuntos);
	}

	private void envioMensajeMailAdj(String mailEmisor, String mailReceptor,
			String mailCopia, String asunto, String cuerpo,
			List<BodyPart> adjuntos) throws Throwable {
		System.out.println("se va a enviar el mail");
		MimeMultipart multiParte = new MimeMultipart();
		for (BodyPart bodyPart : adjuntos) {
			multiParte.addBodyPart(bodyPart);
		}
		MimeMessage mimeMensaje = new MimeMessage(mailSession);
		mimeMensaje.setFrom(new InternetAddress(mailEmisor));

		mimeMensaje.addRecipients(Message.RecipientType.TO,
				destinatariosMail(Arrays.asList(mailReceptor.split(";"))));
		if (!(mailCopia.equals(""))) {
			mimeMensaje.addRecipients(Message.RecipientType.CC,
					destinatariosMail(Arrays.asList(mailCopia.split(";"))));
		}
		mimeMensaje.setSubject(asunto);
		mimeMensaje.setContent(multiParte);
		mimeMensaje.addHeader("Disposition-Notification-To", mailEmisor);
		Transport.send(mimeMensaje);
		System.out.println("envio con exito");
	}

	private BodyPart obtenerAdjuntoMemoria(byte[] bytes, String tipo,
			String nombreArchivo) {
		try {
			BodyPart adjuntoPdf = new MimeBodyPart();
			DataSource dsPdf = new ByteArrayDataSource(bytes, tipo);
			adjuntoPdf.setDataHandler(new DataHandler(dsPdf));
			adjuntoPdf.setFileName(nombreArchivo);

			return adjuntoPdf;
		} catch (MessagingException e) {
			e.printStackTrace();
			return null;
		}
	}

	private BodyPart obtenerAdjuntoTexto(String texto, String tipo,
			String nombreArchivo) {
		try {
			BodyPart adjuntoXml = new MimeBodyPart();
			adjuntoXml.setDataHandler(new DataHandler(texto, tipo));
			adjuntoXml.setFileName(nombreArchivo);
			return adjuntoXml;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private BodyPart obtenerCuerpo(String mensaje) {
		try {
			BodyPart texto = new MimeBodyPart();
			texto.setText(mensaje);
			return texto;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
