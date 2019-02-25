package service;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;


public class AgendamentoEmail {
	Timer timer ;
	
	public AgendamentoEmail(int hora, int minuto) {
		
		Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.HOUR_OF_DAY, hora);
	    calendar.set(Calendar.MINUTE, minuto);
	    calendar.set(Calendar.SECOND, 0);
	    Date time = calendar.getTime();
	    
	    timer = new Timer();
		timer.schedule(new RemindTask(), time);
	}
	
	public void cancelarAgendamento() {
		this.timer.cancel();
		this.timer.purge();
	}
	
	
	class RemindTask extends TimerTask {
        public void run() {
        		
        	Object[] botoes = { "Sim", "Não" };
    		int resposta = JOptionPane.showOptionDialog(null,
    				"O envio de emails foi acionado. Deseja prosseguir?",
    				"Confirmação", 
    				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
    				botoes, botoes[0]);
    		if(resposta == 0) {

    			JOptionPane.showMessageDialog(null, "Rotina de Envio de Boletos foi iniciada.", "", JOptionPane.INFORMATION_MESSAGE);
    			new EnvioBoletoEmail();
    		    JOptionPane.showMessageDialog(null, "Rotina de Envio de Boletos foi concluída.", "", JOptionPane.INFORMATION_MESSAGE);
    			
                timer.cancel(); //Terminate the timer thread
    		}
        }
    }


	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	
}
