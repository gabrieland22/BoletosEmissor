package service;

import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AgendamentoEmail {
  Timer timer;

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
    timer.cancel();
    timer.purge();
  }

  class RemindTask extends TimerTask {
    @Override
    public void run() {
      String contaEmail = "";
      String senha = "";
      Object[] botoes = { "Sim", "Não" };
      int resposta = JOptionPane.showOptionDialog(null,
          "O envio de emails foi acionado. Deseja prosseguir?", "Confirmação",
          JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
          botoes, botoes[0]);
      if (resposta == 0) {

        JTextField jtfConta = new JTextField(30);
        JTextField jtfSenha = new JTextField(30);//

        JPanel panelInputContaSenha = new JPanel();
        GridLayout experimentLayout = new GridLayout(0, 1);
        panelInputContaSenha.setLayout(experimentLayout);

        panelInputContaSenha.add(new JLabel("Conta:"));
        panelInputContaSenha.add(jtfConta);

        panelInputContaSenha.add(new JLabel("Senha:"));
        panelInputContaSenha.add(jtfSenha);

        int result = JOptionPane.showConfirmDialog(null, panelInputContaSenha,
            "Favor informar a conta emissora e sua senha.",
            JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
          contaEmail = jtfConta.getText();
          senha = jtfSenha.getText();
          JOptionPane.showMessageDialog(null,
              "Rotina de Envio de Boletos foi iniciada.", "",
              JOptionPane.INFORMATION_MESSAGE);
          new EnvioBoletoEmail(contaEmail, senha);
          JOptionPane.showMessageDialog(null,
              "Rotina de Envio de Boletos foi concluída.", "",
              JOptionPane.INFORMATION_MESSAGE);
          timer.cancel(); // Terminate the timer thread
        }
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
