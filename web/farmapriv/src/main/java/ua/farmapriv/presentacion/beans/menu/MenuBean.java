package ua.farmapriv.presentacion.beans.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.modena.component.menu.ModenaMenu;

import ua.farmapriv.servicios.modelos.seguridad.Opcion;
import ua.farmapriv.servicios.servicios.seguridad.IServicioSeguridad;
import ua.farmapriv.servicios.utilerias.NombresParametros;

@ManagedBean(name="menuMB")
@SessionScoped
public class MenuBean extends ModenaMenu implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB(lookup=NombresParametros.JNDI_SERVICIO_SEGURIDAD)
	private IServicioSeguridad servicioSeguridad;
	
	private MenuModel modeloMenu;
	
	@PostConstruct
	public void init(){
		System.err.println("cargando menu");
		modeloMenu = cargarMenu();
	}
	
	public MenuModel getModeloMenu() {
		return modeloMenu;
	}
	public void setModeloMenu(MenuModel modeloMenu) {
		this.modeloMenu = modeloMenu;
	}

	private MenuModel cargarMenu() {
		MenuModel menu = new DefaultMenuModel();
		List<Opcion> opcionesPadre = getOpcionesPadre();

		for (Opcion opcionPadre : opcionesPadre) {
			DefaultSubMenu subMenu = new DefaultSubMenu(opcionPadre.getNombre());
			subMenu.setLabel(opcionPadre.getNombre());
			subMenu.setIcon(opcionPadre.getIcono());
			List<Opcion> opcionesHijas = getOpcionesHijas(opcionPadre);
			for (Opcion opcionHija : opcionesHijas) {
				subMenu.getElements().add(getElementoSubmenu(opcionHija));
			}
			menu.addElement(subMenu);
		}
		return menu;
	}

	private MenuElement getElementoSubmenu(Opcion opcionHija) {
		List<Opcion> opcionesHijas = getOpcionesHijas(opcionHija);
		if (opcionesHijas.size() < 1) {
			DefaultMenuItem menItm = new DefaultMenuItem(opcionHija.getNombre());
			menItm.setImmediate(true);
			menItm.setValue(opcionHija.getNombre());
			menItm.setAjax(false);
			menItm.setIcon(opcionHija.getIcono());
			menItm.setTitle(opcionHija.getNombre());
			menItm.setUrl(opcionHija.getRutaAplicacion());
			return menItm;
		} else {
			// si tiene hijas entonces es un submenu
			DefaultSubMenu subMenu = new DefaultSubMenu(opcionHija.getNombre());
			subMenu.setLabel(opcionHija.getNombre());
			subMenu.setIcon(opcionHija.getIcono());
			for (Opcion opcion : opcionesHijas) {
				subMenu.getElements().add(getElementoSubmenu(opcion));
			}
			return subMenu;
		}
	}

	private List<Opcion> getOpcionesPadre() {
		// Se deb cargar desde un ejb
		// la consulta debe utilizar el usuario principal y traer las opciones
		// que tiene permitidas
		List<Opcion> opcionesPadres = servicioSeguridad.obtOpcPad();
		List<Opcion> opcionesPermitidas = new ArrayList<Opcion>();
		Subject usuarioActual = SecurityUtils.getSubject();
		for(Opcion opcion : opcionesPadres){
			if(usuarioActual.isPermitted(opcion.getNombre()+":*"))
				opcionesPermitidas.add(opcion);
		}
		return opcionesPermitidas;
	}

	private List<Opcion> getOpcionesHijas(Opcion opcionPadre) {
		// Se deb cargar desde un ejb
		// Se debe verificar que estas opciones son permitidas para el usuario
		// getUserPrincipal
		List<Opcion> listaRamasMenu = servicioSeguridad.obtOpcHij(opcionPadre);
		List<Opcion> opcionesPermitidas = new ArrayList<Opcion>();
		Subject usuarioActual = SecurityUtils.getSubject();
		for(Opcion opcion : listaRamasMenu){
			if(usuarioActual.isPermitted(opcion.getNombre()+":*"))
				opcionesPermitidas.add(opcion);
		}
		return opcionesPermitidas;
	}
}