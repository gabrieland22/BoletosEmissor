/*
   * Gera atendimentos de forma assíncrona para a Integração dos Cooperados da
   * Unimed
   */
  @Asynchronous
  public void importarRegistrosUnimed(IntegracaoUnimed integracaoUnimed, Procedimento procedimentoSelecionado, HSSFSheet sheet, AvisoSistema avisoSistema, Usuario usuario) throws AppException {
    TempoProcessamentoLog tempoProcessamentoLog = new TempoProcessamentoLog(
        null, usuario.getUsuario(), "Asynchronous", "integração",
        "integração unimed cooperados",
        "Asynchronous importar unimed cooperado", "unimed cooperado");
    Long registro = 0L;
    Iterator<Row> rowIterator = sheet.iterator();
    String codigoAtendimento = null;
    Atendimento atendimento = null;
    Procedimento procedimento = null;
    DadosComplementares dadosComplementares = null;
    PagamentoProcedimento pagamentoProcedimento = null;
    Espelho espelho = null;
    EntidadeHospital entidadeHospital = new EntidadeHospital();
    EntidadeConvenio entidadeConvenio = new EntidadeConvenio();
    Date dataExcel = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date dataVencimento = null;
    sdf.format(new Date());
    String campo = "";
    Double campoNumerico = null;
    String campoString = "";
    Long campoItemDespesa = null;
    List<Atendimento> listaAtendimentos = new ArrayList<Atendimento>();
    Map<String, Cooperado> mapCooperados = new HashMap<String, Cooperado>();
    Map<String, EntidadeCooperadoEspecialidade> mapEntidadeCooperadoEspecialidade = new HashMap<String, EntidadeCooperadoEspecialidade>();
    Map<String, ItemDespesa> mapItemDespesas = new HashMap<String, ItemDespesa>();
    Map<Long, List<Long>> mapProcedimentos = new HashMap<Long, List<Long>>();
    List<Long> idsProcedimentos = new ArrayList<Long>();
    Map<String, EntidadeGrauParticipacao> mapEntidadeGrauParticipacao = new HashMap<String, EntidadeGrauParticipacao>();

    // Acomodações
    TabelaTiss acomodacaoApartamento = new TabelaTiss();
    acomodacaoApartamento.setId(TabelaTiss.ACOMODACAO.APARTAMENTO[0]);
    TabelaTiss acomodacaoEnfermaria = new TabelaTiss();
    acomodacaoEnfermaria.setId(TabelaTiss.ACOMODACAO.ENFERMARIA[0]);
    TabelaTiss acomodacaoExterno = new TabelaTiss();
    acomodacaoExterno.setId(TabelaTiss.ACOMODACAO.EXTERNO[0]);
    TabelaTiss acomodacaoHospitalDia = new TabelaTiss();
    acomodacaoHospitalDia.setId(TabelaTiss.ACOMODACAO.HOSPITAL_DIA);
    TabelaTiss acomodacaoCTI = new TabelaTiss();
    acomodacaoCTI.setId(TabelaTiss.ACOMODACAO.UTICTI);

    // Grau de Participação
    TabelaTiss grauParticipacao = new TabelaTiss();
    EntidadeGrauParticipacao entidadeGrauParticipacao = new EntidadeGrauParticipacao();
    entidadeGrauParticipacao.setEntidade(integracaoUnimed.getEntidade());

    // Via de Acesso
    TabelaTiss viaAcesso = new TabelaTiss();
    viaAcesso.setId(TabelaTiss.VIA_DE_ACESSO.UNICA);
    Boolean erroCalculo = false;
    RotinaSistema rotinaSistemaObj = new RotinaSistema();

    usuarioLogado = usuario;
    Long rotinaSistema = rotinaSistemaDAO.iniciaRotinaSistema(
        integracaoUnimed.getEntidade(), usuarioLogado,
        EnumRotinaSistema.IMPORTACAO_UNIMED_COOPERADO);
    rotinaSistemaObj = rotinaSistemaDAO.recuperaPorId(rotinaSistema);

    try {
      System.out
          .println("Integração Unimed Cooperados - Total de Linhas do arquivo: "
              + sheet.getLastRowNum());
      campo = "Busca de Entidade Convênio";
      entidadeConvenio = entidadeConvenioDAO
          .recuperaEntidadeConvenioPorEntidadeAndConvenio(
              integracaoUnimed.getEntidade(), integracaoUnimed.getConvenio());
      campo = "Busca de Entidade Hospital";
      entidadeHospital.setEntidade(integracaoUnimed.getEntidade());
      entidadeHospital.setHospital(integracaoUnimed.getHospital());
      entidadeHospital = entidadeHospitalDAO
          .recuperaEntidadeHospitalPorEntidadeHospital(entidadeHospital);
      while (rowIterator.hasNext()) {
        Row row = rowIterator.next();

        campo = "Cabeçalho";
        if (row.getCell(0) == null
            || "".equals(row.getCell(0).getStringCellValue().trim())
            || "Semper".equals(row.getCell(0).getStringCellValue().trim())
            || "Registro".equals(row.getCell(0).getStringCellValue().trim())
            || "WPD - Informatica".equals(row.getCell(0).getStringCellValue()
                .trim())) {
          registro++;
          continue;
        }
        avisoSistema.setDescricao("Processando Integração Unimed - Registro "
            + registro + " de " + sheet.getLastRowNum());
        genericoDAO.persist(avisoSistema);

        campo = "Percentual";
        BigDecimal percentual = new BigDecimal(registro)
            .multiply(new BigDecimal("100"));
        percentual = percentual.divide(new BigDecimal(sheet.getLastRowNum()),
            BigDecimal.ROUND_UP);
        rotinaSistemaObj.setPercentualAtual(percentual);
        rotinaSistemaDAO.persistAssincrono(rotinaSistemaObj, usuarioLogado);
        // rotinaSistemaDAO.atualizaRotinaSistema(rotinaSistema, percentual);

        erroCalculo = false;
        campo = "Dados do Atendimento";
        if (codigoAtendimento == null
            || !codigoAtendimento.equals(row.getCell(0).getStringCellValue()
                .trim())) {
          if (codigoAtendimento != null) {
            mapProcedimentos.put(atendimento.getId(), idsProcedimentos);
            idsProcedimentos = new ArrayList<Long>();
          }
          codigoAtendimento = row.getCell(0).getStringCellValue().trim();
          // Atendimento
          campo = "Registro do Atendimento";
          atendimento = new Atendimento();
          campo = "Integracao Unimed";
          atendimento.setIntegracaoUnimed(integracaoUnimed);
          campo = "Entidade";
          atendimento.setEntidade(integracaoUnimed.getEntidade());
          campo = "Entidade Convênio";
          atendimento.setEntidadeConvenio(entidadeConvenio);
          campo = "Entidade Hospital";
          atendimento.setEntidadeHospital(entidadeHospital);
          campo = "Hospital";
          atendimento.setHospital(entidadeHospital.getHospital());
          campo = "Paciente";
          atendimento.setPaciente(row.getCell(1) != null ? row.getCell(1)
              .getStringCellValue().trim() : "");
          campo = "Matrícula do Paciente";
          campoNumerico = row.getCell(8) != null
              && row.getCell(8).getCellType() == 0 ? row.getCell(8)
              .getNumericCellValue() : null;
          campoString = "";
          if (campoNumerico != null && campoNumerico > 0) {
            campoString = retiraCaracteres(campoNumerico.toString());
          }
          atendimento.setMatriculaPaciente(campoString);
          campo = "Número Guia";
          campoNumerico = row.getCell(15) != null
              && row.getCell(15).getCellType() == 0 ? row.getCell(15)
              .getNumericCellValue() : null;
          campoString = "";
          if (campoNumerico != null && campoNumerico > 0) {
            campoString = retiraCaracteres(campoNumerico.toString());
          }
          atendimento.setNumeroGuia(campoString);
          campo = "Guia Principal";
          atendimento.setGuiaPrincipal(campoString);
          campo = "Senha";
          campoNumerico = row.getCell(18) != null
              && row.getCell(18).getCellType() == 0 ? row.getCell(18)
              .getNumericCellValue() : null;
          campoString = "";
          if (campoNumerico != null && campoNumerico > 0) {
            campoString = retiraCaracteres(campoNumerico.toString());
          }
          atendimento.setSenha(campoString);

          campo = "Data Cadastro";
          atendimento.setDataCadastro(new Date());
          campo = "Data Entrega";
          atendimento.setDataEntrega(new Date());

          campo = "Data do Excel";
          dataExcel = null;
          if (row.getCell(9) != null) {
            if (row.getCell(9).getCellType() == 0) {
              if (row.getCell(9).getDateCellValue() != null) {
                dataExcel = row.getCell(9).getDateCellValue();
              }
            } else {
              if (row.getCell(9).getStringCellValue() != null
                  && row.getCell(9).getStringCellValue() != "") {
                dataExcel = sdf.parse(row.getCell(9).getStringCellValue());
              }
            }
          }

          campo = "Data Alta";
          atendimento.setDataAlta(dataExcel);
          campo = "Data Internação";
          atendimento.setDataInternacao(dataExcel);
          campo = "Data Realização";
          atendimento.setDataRealizacao(dataExcel);

          campo = "Hora do Excel";
          if (row.getCell(19) != null) {
            Integer hours = Integer.parseInt(row.getCell(19)
                .getStringCellValue().substring(0, 2));
            Integer minutes = Integer.parseInt(row.getCell(19)
                .getStringCellValue().substring(3, 5));
            dataExcel.setHours(hours);
            dataExcel.setMinutes(minutes);

            campo = "Hora Internação";
            atendimento.setHoraInternacao(dataExcel);
            campo = "Hora Alta";
            atendimento.setHoraAlta(dataExcel);
          }

          campo = "Gravação do Atendimento";
          atendimento.setAutorizadoUnimed(true);
          atendimento.setSituacaoAtendimento(EnumSituacaoAtendimento.DIGITADO);
          atendimento.setNumeroImportacao(integracaoUnimed
              .getNumeroIntegracao());
          // atendimentoDAO.persistAssincrono(atendimento, usuarioLogado);
          genericoDAO.geraNumeroAutomaticoAssincrono(atendimento,
              integracaoUnimed.getEntidade(), false, usuarioLogado);
          genericoDAO.persist(atendimento);
          listaAtendimentos.add(atendimento);

          // Dados Complementares
          dadosComplementares = new DadosComplementares();
          campo = "Dados Complementares";
          dadosComplementares.setAtendimento(atendimento);
          campo = "Matrícula do Segurado";
          dadosComplementares.setMatriculaSegurado(atendimento
              .getMatriculaPaciente());
          campo = "Nome do Segurado";
          dadosComplementares.setNomeSegurado(atendimento.getPaciente());
          campo = "Informações Complementares";
          dadosComplementares.setInformacoesComplementares("Registro "
              + codigoAtendimento);
          campo = "Gravação dos Dados Complementares";
          genericoDAO.persist(dadosComplementares);
        }
        // Procedimento
        campo = "Dados do Procedimento";
        procedimento = new Procedimento();

        campo = "Atendimento (Procedimento)";
        procedimento.setAtendimento(atendimento);

        campo = "Cooperado";
        campoNumerico = row.getCell(13) != null
            && row.getCell(13).getCellType() == 0 ? row.getCell(13)
            .getNumericCellValue() : null;
        campoString = "";
        if (campoNumerico != null && campoNumerico > 0) {
          campoNumerico = campoNumerico / 10;
          campoString = retiraCaracteres(campoNumerico.toString());
        }
        if (!mapCooperados.containsKey(campoString)) {
          mapCooperados.put(campoString, entidadeCooperadoDAO
              .recuperaCooperadoPorCodigoConselho(campoString,
                  integracaoUnimed.getEntidade()));
        }
        procedimento.setCooperadoExecutanteComplemento(mapCooperados
            .get(campoString));
        procedimento.setCooperadoRecebedorCobranca(mapCooperados
            .get(campoString));

        if (!mapEntidadeCooperadoEspecialidade.containsKey(campoString)) {
          mapEntidadeCooperadoEspecialidade.put(campoString,
              entidadeCooperadoEspecialidadeDAO
                  .recuperaEntidadeCooperadoEspecialidadePorCooperado(
                      procedimento.getCooperadoExecutanteComplemento(),
                      integracaoUnimed.getEntidade()));
        }
        procedimento
            .setEntidadeCooperadoEspecialidade(mapEntidadeCooperadoEspecialidade
                .get(campoString));

        campo = "Item de Despesa";
        campoNumerico = row.getCell(10).toString() != null
            && row.getCell(10).getCellType() == 0 ? row.getCell(10)
            .getNumericCellValue() : null;

        campoString = "";
        if (campoNumerico != null && campoNumerico > 0) {
          campoItemDespesa = campoNumerico.longValue();
        }
        if (!mapItemDespesas.containsKey(campoItemDespesa.toString())) {
          mapItemDespesas.put(campoItemDespesa.toString(), itemDespesaDAO
              .recuperaItemDespesaPorCodigoPadrao(campoItemDespesa.toString()));
        }
        procedimento.setItemDespesa(mapItemDespesas.get(campoItemDespesa
            .toString()));

        campo = "Acomodação";
        if (row.getCell(3) == null
            || row.getCell(3).getStringCellValue().trim() == "") {
          procedimento.setAcomodacao(procedimentoSelecionado.getAcomodacao());
        } else {
          if ("EN".equals(row.getCell(3).getStringCellValue().trim())) {
            procedimento.setAcomodacao(acomodacaoEnfermaria);
          } else if ("AP".equals(row.getCell(3).getStringCellValue().trim())) {
            procedimento.setAcomodacao(acomodacaoApartamento);
          } else if ("HD".equals(row.getCell(3).getStringCellValue().trim())) {
            procedimento.setAcomodacao(acomodacaoHospitalDia);
          } else if ("CT".equals(row.getCell(3).getStringCellValue().trim())) {
            procedimento.setAcomodacao(acomodacaoCTI);
          } else {
            procedimento.setAcomodacao(acomodacaoExterno);
          }
        }

        campo = "Via de Acesso";
        procedimento.setViaAcesso(viaAcesso);
        campo = "Tipo de Guia";
        // procedimento.setTipoGuia(tipoGuia);

        campo = "Quantidade";
        campoNumerico = row.getCell(12) != null
            && row.getCell(12).getCellType() == 0 ? row.getCell(12)
            .getNumericCellValue() : null;
        campoString = "";
        if (campoNumerico != null && campoNumerico > 0) {
          campoNumerico = campoNumerico / 10;
          campoString = retiraCaracteres(campoNumerico.toString());
        } else {
          campoString = "1";
        }
        procedimento.setQuantidade(Long.parseLong(campoString));
        campo = "Data Realização (Procedimento)";

        campo = "Data do Excel";
        dataExcel = null;
        if (row.getCell(9) != null) {
          if (row.getCell(9).getCellType() == 0) {
            if (row.getCell(9).getDateCellValue() != null) {
              dataExcel = row.getCell(9).getDateCellValue();
            }
          } else {
            if (row.getCell(9).getStringCellValue() != null
                && row.getCell(9).getStringCellValue() != "") {
              dataExcel = sdf.parse(row.getCell(9).getStringCellValue());
            }
          }
        }
        procedimento.setDataRealizacao(dataExcel);
        campo = "Data Inicio";
        procedimento.setDataInicio(dataExcel);
        campo = "Data Fim";
        procedimento.setDataFim(dataExcel);

        campo = "Hora do Excel";
        if (row.getCell(19) != null) {
          Integer hours = Integer.parseInt(row.getCell(19).getStringCellValue()
              .substring(0, 2));
          Integer minutes = Integer.parseInt(row.getCell(19)
              .getStringCellValue().substring(3, 5));
          dataExcel.setHours(hours);
          dataExcel.setMinutes(minutes);

          campo = "Hora Início";
          procedimento.setHoraInicio(dataExcel);

          campo = "Hora Fim";
          procedimento.setHoraFim(dataExcel);
        }

        campo = "Ato";
        campoNumerico = row.getCell(16) != null
            && row.getCell(16).getCellType() == 0 ? row.getCell(16)
            .getNumericCellValue() : null;

        buscaAto(campoNumerico, entidadeGrauParticipacao);

        if (campoNumerico != null && campoNumerico > 0) {
          if (!mapEntidadeGrauParticipacao
              .containsKey(campoNumerico.toString())) {
            mapEntidadeGrauParticipacao
                .put(campoNumerico.toString(), entidadeGrauParticipacaoDAO
                    .recuperaEntidadeGrauParticipacao(entidadeGrauParticipacao));
          }
        }

        procedimento.setEntidadeGrauParticipacao(mapEntidadeGrauParticipacao
            .get(campoNumerico.toString()));

        campo = "Valor Percentual";
        campoNumerico = row.getCell(17) != null
            && row.getCell(17).getCellType() == 0 ? row.getCell(17)
            .getNumericCellValue() : null;
        if (campoNumerico != null && campoNumerico > 0) {
          procedimento.setValorPercentual(new BigDecimal(campoNumerico
              .toString()));
        }

        procedimento.setFormaExecucao(EnumFormaExecucao.ESPECIAL);

        campo = "Gravação do Procedimento";
        procedimento.setAutorizadoUnimed(true);
        procedimento.setForcarAtendimento(false);
        procedimento.setTuss(false);
        genericoDAO.persist(procedimento);
        idsProcedimentos.add(procedimento.getId());
        registro++;
      }
      // Adiciona última lista de procedimentos
      mapProcedimentos.put(atendimento.getId(), idsProcedimentos);

      avisoSistema
          .setDescricao("Processando Integração Unimed - Calculando Valores");
      genericoDAO.persist(avisoSistema);

      erroCalculo = true;
      String numeroAtendimentoInicial = "";
      String numeroAtendimentoFinal = "";
      Integer totalAtendimentos = listaAtendimentos.size();
      Integer atendimentoAtual = 0;

      for (Atendimento atend : listaAtendimentos) {
        atendimentoAtual++;
        if (atendimentoAtual == 1 || atendimentoAtual % 10 == 0) {
          System.out
              .println("Integração Unimed Cooperados - Calculando atendimento "
                  + atendimentoAtual + " de " + totalAtendimentos);
        }
        if (numeroAtendimentoInicial.equals("")) {
          numeroAtendimentoInicial = atend.getNumeroAtendimentoAutomatico()
              .toString();
        }
        numeroAtendimentoFinal = atend.getNumeroAtendimentoAutomatico()
            .toString();
        // Calcula o atendimento e seus procedimentos
        campo = "Cálculo de Atendimento. Guia " + atend.getGuiaPrincipal()
            + " / Paciente " + atend.getPaciente();

      }

      // Geração do Espelho
      // Retirada conforme notificação na tarefa #2005
      /*
       * campo = "Geração do Espelho";
       * avisoSistema
       * .setDescricao("Processando Integração Unimed - Geração do Espelho");
       * genericoDAO.persist(avisoSistema);
       * 
       * try {
       * dataVencimento = calculoDataVencimentoEntidadeConvenioService
       * .calcular(entidadeConvenio);
       * if (dataVencimento == null) {
       * dataVencimento = new Date();
       * }
       * } catch (Exception e) {
       * campo = " Geração de Data de Vencimento do Espelho";
       * }
       * 
       * espelho = new Espelho();
       * espelho.setEntidade(integracaoUnimed.getEntidade());
       * espelho.setEntidadeConvenio(entidadeConvenio);
       * espelho.setHospital(integracaoUnimed.getHospital());
       * espelho.setDataVencimento(dataVencimento);
       * campo = "Geração do Número do Espelho";
       * genericoDAO.geraNumeroAutomatico(espelho,
       * integracaoUnimed.getEntidade(),
       * false);
       * for (Atendimento a : listaAtendimentos) {
       * a.setEspelho(espelho);
       * a.setSituacaoAtendimento(EnumSituacaoAtendimento.ESPELHADO);
       * atendimentoDAO.persistAssincrono(a, usuarioLogado);
       * }
       * 
       * avisoSistema
       * .setDescricao("Processando Integração Unimed - Cálculo do Espelho");
       * genericoDAO.persist(avisoSistema);
       * 
       * campo = "Cálculo do Espelho";
       * calculoEspelhoService.calcular(espelho);
       * 
       * avisoSistema
       * .setDescricao("Processando Integração Unimed - Gravação do Espelho");
       * genericoDAO.persist(avisoSistema);
       * 
       * campo = "Gravação do Espelho";
       * espelhoDAO.persistAssincrono(espelho, usuarioLogado);
       * integracaoUnimed.setEspelho(espelho);
       * integracaoUnimedDAO.persistAssincrono(integracaoUnimed, usuarioLogado);
       */

      integracaoUnimed.setStatus("Concluído: Atendimentos de "
          + numeroAtendimentoInicial + " a " + numeroAtendimentoFinal);
      integracaoUnimed.setNumeroIntegracao(integracaoUnimed.getId() + 1L);
      integracaoUnimedDAO.persistAssincrono(integracaoUnimed, usuarioLogado);
      Calendar data = Calendar.getInstance();
      data.add(Calendar.SECOND, 15);
      avisoSistema
          .setDescricao("Processo de Integração Unimed finalizado. Atendimentos de "
              + numeroAtendimentoInicial + " a " + numeroAtendimentoFinal);
      avisoSistema.setDataHoraFim(data.getTime());
      genericoDAO.persist(avisoSistema);

      rotinaSistemaObj.setDataHoraFim(new Date());
      rotinaSistemaObj.setUsuarioFim(usuarioLogado);
      rotinaSistemaObj.setPercentualAtual(new BigDecimal(100));
      rotinaSistemaDAO.persistAssincrono(rotinaSistemaObj, usuarioLogado);
      rotinaSistemaObj = null;

      System.out.println("Integração Unimed Cooperados - Concluída");
    } catch (Exception e) {
      registro++;
      String mensagemErro = "Erro no Registro " + codigoAtendimento
          + ". Campo " + campo + ". Linha " + registro;
      if (erroCalculo) {
        mensagemErro = "Erro no processo de " + campo;
      }
      integracaoUnimed.setErroNaImportacao(true);
      integracaoUnimed.setStatus(mensagemErro);
      integracaoUnimed.setNumeroIntegracao(integracaoUnimed.getId() + 1L);
      genericoDAO.persist(integracaoUnimed);
      Calendar data = Calendar.getInstance();
      data.add(Calendar.SECOND, 15);
      avisoSistema.setDescricao(mensagemErro);
      avisoSistema.setDataHoraFim(data.getTime());
      genericoDAO.persist(avisoSistema);

      rotinaSistemaDAO.finalizaRotinaSistema(rotinaSistema, usuarioLogado);
    }
    integracaoUnimed = null;
    procedimentoSelecionado = null;
    avisoSistema = null;
    tempoProcessamentoLog.fim();
  }