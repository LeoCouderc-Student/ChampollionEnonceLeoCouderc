package champollion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Enseignant extends Personne {

    // TODO : rajouter les autres méthodes présentes dans le diagramme UML

	private Map<Intervention, UE> interventionPlanifiees;
	private Map<UE, ServicePrevu> enseignements;

    public Enseignant(String nom, String email) {
        super(nom, email);
        
        this.interventionPlanifiees = new HashMap<Intervention, UE>();
        this.enseignements = new HashMap<UE, ServicePrevu>();
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant en "heures équivalent TD" Pour le calcul : 1 heure
     * de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure de TP vaut 0,75h
     * "équivalent TD"
     *
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevues() {
    	float resultat = 0F;
    	
    	for (ServicePrevu service: this.enseignements.values()) {
    		resultat += service.getVolumeCM() * 1.5F + service.getVolumeTD() + service.getVolumeTP() * 0.75F;
		}
        return (int) resultat;
    }

    /**
     * Calcule le nombre total d'heures prévues pour cet enseignant dans l'UE spécifiée en "heures équivalent TD" Pour
     * le calcul : 1 heure de cours magistral vaut 1,5 h "équivalent TD" 1 heure de TD vaut 1h "équivalent TD" 1 heure
     * de TP vaut 0,75h "équivalent TD"
     *
     * @param ue l'UE concernée
     * @return le nombre total d'heures "équivalent TD" prévues pour cet enseignant, arrondi à l'entier le plus proche
     *
     */
    public int heuresPrevuesPourUE(UE ue) {
    	ServicePrevu specifiedService = this.enseignements.get(ue);
        return (int) (specifiedService.getVolumeCM() * 1.5F + specifiedService.getVolumeTD() + specifiedService.getVolumeTP() * 0.75F);
    }

    /**
     * Ajoute un enseignement au service prévu pour cet enseignant
     *
     * @param ue l'UE concernée
     * @param volumeCM le volume d'heures de cours magitral
     * @param volumeTD le volume d'heures de TD
     * @param volumeTP le volume d'heures de TP
     */
    public void ajouteEnseignement(UE ue, int volumeCM, int volumeTD, int volumeTP) {
    		
    	if(!this.enseignements.containsKey(ue)) {
    		ServicePrevu service = new ServicePrevu(volumeTD, volumeTP, volumeCM);
    		this.enseignements.put(ue, service);
    	} else {
    		ServicePrevu service = this.enseignements.get(ue);
    		service.setVolumeCM(volumeCM + service.getVolumeCM());
    		service.setVolumeTD(volumeTD + service.getVolumeTD());
    		service.setVolumeTP(volumeTP + service.getVolumeTP());
    	}
    }
    
    /**
     * Détermine si un enseignant a fait moins de 192h
     */
    public boolean enSousService() {
    	return this.heuresPrevues() < 192;
    }
    
    /**
     * ajoute une intervention planifiée pour cet enseignant
     */
    public void ajouteIntervention(UE ue, Intervention intervention) {
    	this.interventionPlanifiees.put(intervention, ue);
    }
    
    /**
     * Nombre d'heure restante à faire pour une eu et un type donnée
     */
    public int resteAPlanifier(UE ue, TypeIntervention type) {
    	float heureFaite = 0F;
    	List<Intervention> list = new ArrayList<Intervention>(this.interventionPlanifiees.keySet());
    	for (Intervention intervention : list)
    	{
			if ( !intervention.getAnnulee() && this.interventionPlanifiees.get(intervention).equals(ue) && type.equals(intervention.getType())) {
				heureFaite += intervention.getDuree();
			}
		}
    	return (int) (this.heuresPrevuesPourUE(ue) - heureFaite);
    }

}
