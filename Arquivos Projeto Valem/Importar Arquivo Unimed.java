@SuppressWarnings("resource")
  public String importarArquivo() throws IOException, ParseException {
    TempoProcessamentoLog tempoProcessamentoLog = new TempoProcessamentoLog(
        loginController.getEntidadeSelecionada().getSigla(), loginController
            .getUsuario().getUsuario(), null, "integração",
        "integração unimed cooperados", "importar", "unimed cooperado");
    if (importarArquivoAntes()) {
      Boolean erroExtensao = false;
      String extensao = file.getFileName().substring(
          file.getFileName().lastIndexOf("."), file.getFileName().length());

      if (!extensao.toUpperCase().equals(".XLS")) {
        erroExtensao = true;
        MessageUtil.addTextoLivreErro("A extensão do arquivo deve ser XLS.");
      }

      if (!erroExtensao) {
        final File tempFile = File.createTempFile("tempfile", ".xls");
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
          IOUtils.copy(file.getInputstream(), out);
        }

        HSSFWorkbook workbook = null;
        HSSFSheet sheet = null;

        workbook = new HSSFWorkbook(file.getInputstream());
        sheet = workbook.getSheetAt(0);

        MessageUtil.addTextoLivreAlerta("Processando Integração Unimed");
        atualizarBlocoMensagem();

        try {
          integracaoUnimed.setStatus("Processando");
          integracaoUnimed.setDataHoraIntegracao(new Date());
          integracaoUnimed.setArquivo(file.getFileName());
          integracaoUnimed = integracaoUnimedService.persist(integracaoUnimed);
          integracaoUnimed.setNumeroIntegracao(integracaoUnimed.getId() + 1L);
          integracaoUnimedService.persist(integracaoUnimed);

          AvisoSistema avisoSistema = atualizarMensagemSistema();
          integracaoUnimedService.importarRegistrosUnimed(integracaoUnimed,
              procedimentoSelecionado, sheet, avisoSistema,
              loginController.getUsuario());

          pesquisar();

          MessageUtil
              .addTextoLivreErro("Atendimentos gerados atraves da Integração Unimed deverão ser recalculados através da tela de Tratar Atendimento.");

        } catch (Exception e) {
          MessageUtil
              .addTextoLivreErro("Ocorreu um erro no Processando de Integração da Unimed: "
                  + e.getMessage());
          atualizarBlocoMensagem();
        }
      }
      tempoProcessamentoLog.fim();
      return "integracao_unimed_pesquisa.page";
    }
    tempoProcessamentoLog.fim();
    return "integracao_unimed_importar.page";
  }